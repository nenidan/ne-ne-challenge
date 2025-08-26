package com.github.nenidan.ne_ne_challenge.domain.payment.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentCancelCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentSearchCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentCancelResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentPrepareResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentSearchResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentStatisticsResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.TossConfirmResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.mapper.PaymentApplicationMapper;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.vo.Money;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.repository.PaymentRepository;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.type.PaymentStatus;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    // ============================= 결제 준비 관련 =============================
    /**
     * 결제 요청 전 orderId와 amount를 DB에 저장합니다.
     */
    @Transactional
    public PaymentPrepareResult preparePayment(Long userId, int requestAmount) {

        Money amount = Money.ofPayment(requestAmount);

        Payment preparePayment = Payment.createPreparePayment(userId, amount);

        Payment savedPreparePayment = paymentRepository.save(preparePayment);

        return PaymentApplicationMapper.toPaymentPrepareResult(savedPreparePayment);
    }

    // ============================= 결제 생성 관련 =============================

    @Transactional
    public Payment markAsSuccess(TossConfirmResult tossConfirmResult, int requestAmount) {

        Payment payment = getPaymentByOrderId(OrderId.of(tossConfirmResult.getOrderId()));
        Money money = Money.of(requestAmount);

        if (!payment.getAmount().equals(money)) {
            throw new PaymentException(PaymentErrorCode.AMOUNT_MISMATCH);
        }

        payment.markAsSuccess(
            tossConfirmResult.getPaymentKey(),
            tossConfirmResult.getStatus(),
            tossConfirmResult.getMethod(),
            tossConfirmResult.getApprovedAt().toLocalDateTime()
        );

        return payment;
    }

    @Transactional
    public void markAsFailed(String orderId, String paymentKey) {

        Payment failedPayment = getPaymentByOrderId(OrderId.of(orderId));

        failedPayment.markAsFailed(paymentKey);

        paymentRepository.save(failedPayment);
    }

    // ============================= 결제 취소 관련 =============================

    @Transactional(readOnly = true)
    public Payment getPaymentForCancel(Long userId, String orderId) {

        Payment payment = getPaymentByOrderId(OrderId.of(orderId));

        payment.validateCancelable(userId);

        return payment;
    }

    @Transactional
    public PaymentCancelResult cancelPayment(Payment payment, PaymentCancelCommand command) {

        payment.cancel(command.getCancelReason());

        Payment savedPayment = paymentRepository.save(payment);

        return PaymentApplicationMapper.toPaymentCancelResult(savedPayment);
    }

    @Transactional
    public void rollbackCancel(String orderId) {

        Payment payment = getPaymentByOrderId(OrderId.of(orderId));

        payment.rollbackCancel();

        paymentRepository.save(payment);
    }

    // ============================= 결제 조회 관련 =============================

    /**
     * 자신의 결제 내역을 커서 기반으로 조회합니다.
     */
    @Transactional(readOnly = true)
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

    /**
     * 사용자 결제 내역 통계를 위한 메서드
     */
    @Transactional(readOnly = true)
    public List<PaymentStatisticsResult> getAllPayments() {
        return paymentRepository.findAll().stream()
            .map(PaymentApplicationMapper::toPaymentStatisticsResult)
            .toList();
    }

    // ============================== private 헬퍼 메서드 ==============================

    private Payment getPaymentByOrderId(OrderId orderId) {
        return paymentRepository.findByOrderId(orderId.getValue())
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


}

