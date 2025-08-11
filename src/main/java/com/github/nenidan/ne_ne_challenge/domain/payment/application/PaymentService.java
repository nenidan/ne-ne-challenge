package com.github.nenidan.ne_ne_challenge.domain.payment.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentCancelCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentSearchCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentCancelResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentSearchResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentStatisticsResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.TossCancelResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.TossConfirmResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.mapper.PaymentApplicationMapper;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.repository.PaymentRepository;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.type.PaymentStatus;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;

    // ============================= 결제 생성 관련 =============================

    /**
     * 요청 금액 검증 및 성공 결제 기록을 생성 후 저장합니다.
     */
    @Transactional
    public Payment createPaymentWithValidation(Long userId, TossConfirmResult result, int amount) {

        // 토스에서 결제한 금액과, 프론트에서 요청한 금액이 맞는지 검증
        validatePaymentAmount(result.getTotalAmount(), amount);

        // 토스 결제 내역을 기반으로 payment 객체 생성
        Payment payment = Payment.createPaymentFromToss(
            userId,
            result.getPaymentKey(),
            result.getOrderId(),
            result.getStatus(),
            result.getMethod(),
            result.getRequestedAt().toLocalDateTime(),
            result.getApprovedAt().toLocalDateTime(),
            result.getTotalAmount()
        );

        return paymentRepository.save(payment);
    }

    /**
     * 결제 검증 실패 시 실패 기록을 생성 및 저장합니다.
     */
    @Transactional
    public Payment createFailPayment(Long userId, TossConfirmResult result) {
        Payment failPayment = Payment.createFailPayment(
            userId,
            result.getPaymentKey(),
            result.getOrderId(),
            result.getMethod(),
            result.getRequestedAt().toLocalDateTime(),
            result.getTotalAmount()
        );

        return paymentRepository.save(failPayment);
    }

    // ============================= 결제 조회 관련 =============================

    /**
     * 자신의 결제 내역을 커서 기반으로 조회합니다.
     */
    public CursorResponse<PaymentSearchResult, Long> searchMyPayments(Long userId, PaymentSearchCommand command) {

        LocalDateTime startDate = convertToStartDateTime(command.getStartDate());
        LocalDateTime endDate = convertToEndDateTime(command.getEndDate());
        String paymentStatus = convertToStatusString(command.getStatus());

        List<PaymentSearchResult> paymentSearchResultList = paymentRepository.searchPayments(
            userId,
            command.getCursor(),
            paymentStatus,
            startDate,
            endDate,
            command.getSize() + 1
            )
            .stream()
            .map(PaymentApplicationMapper::toPaymentResult)
            .toList();

        return CursorResponse.of(paymentSearchResultList, PaymentSearchResult::getPaymentId, command.getSize());
    }

    // ============================= 결제 취소 관련 =============================

    public Payment validatePaymentForCancel(Long userId, String orderId) {
        // payment 조회
        Payment payment = getPaymentByOrderId(orderId);

        if (!payment.getUserId().equals(userId)) {
            throw new PaymentException(PaymentErrorCode.PAYMENT_ACCESS_DENIED);
        }

        // 취소가 가능한 결제 내역인지 확인, 만약 취소가 안된다면 예외가 터진다.
        payment.validateCancelable();

        return payment;
    }

    @Transactional
    public PaymentCancelResult updatePaymentCancel(Payment payment, TossCancelResult tossCancelResult,
        PaymentCancelCommand command) {

        payment.cancel(command.getCancelReason(), tossCancelResult.getCanceledAt().toLocalDateTime(), tossCancelResult.getStatus());

        paymentRepository.save(payment);

        return PaymentApplicationMapper.toPaymentCancelResult(payment);
    }

    public List<PaymentStatisticsResult> getAllPayments() {
        return paymentRepository.findAll().stream()
            .map(PaymentApplicationMapper::toPaymentStatisticsResult)
            .toList();
    }

    // ================= 유틸성 메서드 =================
    private Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId)
            .orElseThrow(() -> new PaymentException(PaymentErrorCode.PAYMENT_NOT_FOUND));
    }

    // 시작일을 LocalDateTime 으로 변환 (00:00:00)
    private LocalDateTime convertToStartDateTime(LocalDate startDate) {
        return (startDate != null) ? startDate.atStartOfDay() : null;
    }


    // 종료일을 LocalDateTime 으로 변환 (23:59:59)
    private LocalDateTime convertToEndDateTime(LocalDate endDate) {
        return (endDate != null) ? endDate.atTime(23, 59, 59) : null;
    }

    // 상태 문자열 정리 (빈 문자열 → null)
    private String convertToStatusString(String status) {
        if (!StringUtils.hasText(status)) {
            return null;
        }

        PaymentStatus paymentStatus = PaymentStatus.of(status);

        return paymentStatus.name();
    }

    // 프론트에서 요청한 금액과, 토스에서 승인된 금액이 같은지 확인하는 메서드
    private void validatePaymentAmount(int totalAmount, int amount) {
        if (totalAmount != amount) {
            throw new PaymentException(PaymentErrorCode.AMOUNT_MISMATCH);
        }
    }
}

