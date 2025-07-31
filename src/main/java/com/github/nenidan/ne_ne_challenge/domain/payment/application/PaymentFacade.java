package com.github.nenidan.ne_ne_challenge.domain.payment.application;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.client.TossClient;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentConfirmCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentPrepareCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PointClientCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentConfirmResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentPrepareResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.TossClientResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.mapper.PaymentApplicationMapper;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;
import com.github.nenidan.ne_ne_challenge.global.client.point.PointClient;
import com.github.nenidan.ne_ne_challenge.global.client.user.UserClient;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentFacade {

    private final PaymentService paymentService;
    private final UserClient userClient;
    private final PointClient pointClient;
    private final TossClient tossClient;

    public PaymentPrepareResult createPreparePayment(Long userId, PaymentPrepareCommand command) {

        userClient.getUserById(userId);

        return paymentService.createPreparePayment(userId, command);
    }

    public PaymentConfirmResult confirmAndChargePoint(Long userId, PaymentConfirmCommand command) {

        // 유저 검증
        userClient.getUserById(userId);

        // 결제 정보 조회 및 금액 검증
        Payment payment = paymentService.validatePaymentForConfirm(
            command.getOrderId(),
            command.getAmount()
        );

        try {
            // 토스 페이먼츠의 /payments/confirm API 호출 (결제 승인)
            TossClientResult tossClientResult = tossClient.confirmPayment(
                command.getPaymentKey(),
                command.getOrderId(),
                command.getAmount()
            );

            // prepare 에서 저장되어있던 payment 객체에 토스 confirm API 에서 전달받은 값 업데이트
            // paymentKey, paymentMethod, paymentStatus, approvedAt 저장
            paymentService.updatePaymentFromConfirm(payment, tossClientResult);

            // 포인트 충전
            PointClientCommand PointClientCommand = PaymentApplicationMapper.toPointClientCommand(
                userId,
                command.getAmount()
            );
            pointClient.chargePoint(PointClientCommand);

        } catch (Exception e) {

            // DB에 실패를 기록함 Status = PENDING -> FAIL, failedAt에 실패 시간 저장
            paymentService.failPayment(payment);
            throw new PaymentException(PaymentErrorCode.PAYMENT_PROCESSING_FAILED, e);
        }

        return PaymentApplicationMapper.toPaymentConfirmResult(payment);
    }

    public CursorResponse<PaymentResult, Long> searchMyPayments(Long userId, Long cursor, int size, String method,
        String status, LocalDate startDate, LocalDate endDate) {

        userClient.getUserById(userId);

        return paymentService.searchMyPayments(userId, cursor, size, method, status, startDate, endDate);
    }

}
