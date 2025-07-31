package com.github.nenidan.ne_ne_challenge.domain.payment.application;

import java.time.LocalDate;

import com.github.nenidan.ne_ne_challenge.global.client.point.PointClient;
import com.github.nenidan.ne_ne_challenge.global.client.user.UserClient;
import org.springframework.stereotype.Service;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.ChargePointCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PointClientCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.mapper.PaymentApplicationMapper;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentService paymentService;
    private final UserClient userClient;
    private final PointClient pointClient;

    public PaymentResult chargePoint(Long userId, ChargePointCommand command) {

        userClient.getUserById(userId);

        Payment payment = paymentService.createChargePayment(userId, command);

        try {
            PointClientCommand pointClientCommand = PaymentApplicationMapper.toPointClientCommand(userId, command.getAmount());
            pointClient.chargePoint(pointClientCommand);

            // 결제 성공 상태 반영
            paymentService.succeedPayment(payment);

        } catch (Exception e) {
            // 결제 실패 상태 반영
            paymentService.failPayment(payment);
            throw new PaymentException(PaymentErrorCode.POINT_CHARGE_FAILED, e);
        }

        return PaymentApplicationMapper.toPaymentResult(payment);
    }

    public CursorResponse<PaymentResult, Long> searchMyPayments(Long userId, Long cursor, int size, String method,
        String status, LocalDate startDate, LocalDate endDate) {

        userClient.getUserById(userId);

        return paymentService.searchMyPayments(userId, cursor, size, method, status, startDate, endDate);
    }

}
