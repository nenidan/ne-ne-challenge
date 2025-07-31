package com.github.nenidan.ne_ne_challenge.domain.payment.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentPrepareCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentSearchCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.TossClientResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentPrepareResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentResult;
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

    public CursorResponse<PaymentResult, Long> searchMyPayments(Long userId, PaymentSearchCommand command) {

        LocalDateTime startDate = convertToStartDateTime(command.getStartDate());
        LocalDateTime endDate = convertToEndDateTime(command.getEndDate());
        String paymentStatus = convertToStatusString(command.getStatus());

        List<PaymentResult> paymentList = paymentRepository.searchPayments(
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

        boolean hasNext = paymentList.size() > command.getSize();

        List<PaymentResult> content = hasNext ? paymentList.subList(0, command.getSize()) : paymentList;

        Long nextCursor = hasNext ? paymentList.get(paymentList.size() - 1).getPaymentId() : null;

        return new CursorResponse<>(content, nextCursor, hasNext);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void failPayment(Payment payment) {
        payment.fail();
        paymentRepository.save(payment);
    }

    @Transactional
    public void updatePaymentFromConfirm(Payment payment, TossClientResult result) {

        payment.updateConfirm(result);
        paymentRepository.save(payment);
    }

    @Transactional
    public PaymentPrepareResult createPreparePayment(Long userId, PaymentPrepareCommand command) {

        Payment preparePayment = Payment.createPreparePayment(userId, command.getAmount());

        Payment savedPreparePayment = paymentRepository.save(preparePayment);

        return PaymentApplicationMapper.toPaymentPrepareResult(savedPreparePayment);
    }

    public Payment validatePaymentForConfirm(String orderId, int amount) {

        // orderId를 이용해서 /payments/prepare API 에서 저장한 payment 객체를 찾음
        Payment payment = getPaymentByOrderId(orderId);

        // 보안 검증 : 클라이언트에서 전달받은 금액과 DB에 저장된 금액이 일치하는지 확인
        payment.validateAmountForConfirm(amount);

        return payment;
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
}

