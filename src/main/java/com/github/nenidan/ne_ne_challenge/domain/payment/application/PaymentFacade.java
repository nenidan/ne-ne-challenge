package com.github.nenidan.ne_ne_challenge.domain.payment.application;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.client.TossClient;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentCancelCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentConfirmCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentPrepareCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentSearchCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentCancelResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentConfirmResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentPrepareResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentSearchResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.TossCancelResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.TossConfirmResult;
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

        TossConfirmResult tossConfirmResult = null;

        try {
            // 토스 페이먼츠의 /payments/confirm API 호출 (결제 승인)
            tossConfirmResult = tossClient.confirmPayment(
                command.getPaymentKey(),
                command.getOrderId(),
                command.getAmount()
            );

            // prepare 에서 저장되어있던 payment 객체에 토스 confirm API 에서 전달받은 값 업데이트
            // paymentKey, paymentMethod, paymentStatus, approvedAt 저장
            paymentService.updatePaymentFromConfirm(payment, tossConfirmResult);

            // 포인트 충전
            pointClient.chargePoint(
                userId,
                command.getAmount(),
                "CHARGE",
                payment.getOrderId()
            );

        } catch (RestClientResponseException e) {

            if (tossConfirmResult == null) {
                // Toss 오류 로그로 남김 (code + message)
                log.warn("Toss 결제 실패 - status: {}, body: {}", e.getRawStatusCode(), e.getResponseBodyAsString());
            } else {
                try {
                    tossClient.cancelPayment(command.getPaymentKey(), "시스템 오류로 인한 결제 취소");
                } catch (Exception cancelEx) {
                    log.error("Toss 결제 취소 실패 - 수동 처리 필요: {}", tossConfirmResult.getPaymentKey());
                }
                paymentService.failPayment(payment);
            }


            paymentService.failPayment(payment);
            throw new PaymentException(PaymentErrorCode.TOSS_ERROR, e);
        }
        catch (Exception e) {

            // 토스 결제 성공 + payment 업데이트 실패, 토스 결제 성공 + payment 업데이트 성공 + point 증가 실패 시 토스 결제 취소 api 호출
            tossClient.cancelPayment(command.getPaymentKey(), "시스템 오류로 인한 결제 취소");

            // DB에 실패를 기록함 Status = PENDING -> FAIL, failedAt에 실패 시간 저장
            paymentService.failPayment(payment);
            throw new PaymentException(PaymentErrorCode.PAYMENT_PROCESSING_FAILED, e);
        }

        return PaymentApplicationMapper.toPaymentConfirmResult(payment);
    }

    public CursorResponse<PaymentSearchResult, Long> searchMyPayments(Long userId, PaymentSearchCommand command) {

        userClient.getUserById(userId);

        return paymentService.searchMyPayments(userId, command);
    }

    public PaymentCancelResult cancelPayment(Long userId, String orderId, PaymentCancelCommand command) {

        userClient.getUserById(userId);

        // 결제 정보 조회 및 기본 검증 (통과 한다면 취소가 가능한 결제 내역이다.)
        Payment payment = paymentService.validatePaymentForCancel(userId, orderId);

        // 그러면 이제 토스 페이먼츠에 내 DB에 저장된 결제 내역에서 paymentKey와 cancelReason을 가지고 취소 요청을 보낸다.
        TossCancelResult tossCancelResult = tossClient.cancelPayment(payment.getPaymentKey(),
            command.getCancelReason());

        log.info("토스 취소 응답: canceledAt={}, status={}",
            tossCancelResult.getCanceledAt(),
            tossCancelResult.getStatus());

        PaymentCancelResult result = paymentService.updatePaymentCancel(payment, tossCancelResult, command);

        // 토스에서 취소가 완료가 되었으면 이제 point 쪽에 취소(포인트를 다시 차감)하라고 요청을 보낸다.
        pointClient.cancelPoint(orderId);

        return result;
    }
}
