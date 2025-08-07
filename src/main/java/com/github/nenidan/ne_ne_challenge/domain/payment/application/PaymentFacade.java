package com.github.nenidan.ne_ne_challenge.domain.payment.application;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.client.TossClient;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentCancelCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentConfirmCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentSearchCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentCancelResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentConfirmResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentSearchResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.TossCancelResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.TossConfirmResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.mapper.PaymentApplicationMapper;
import com.github.nenidan.ne_ne_challenge.global.event.PaymentCompletedEvent;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.event.PointChargeRequested;
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
    private final TossClient tossClient;
    private final ApplicationEventPublisher eventPublisher;
    private final PointClient pointClient;

    public PaymentConfirmResult confirmAndChargePoint(Long userId, PaymentConfirmCommand command) {

        // 유저 검증
        userClient.getUserById(userId);

        Payment payment = null;
        TossConfirmResult tossConfirmResult = null;
        TossCancelResult tossCancelResult = null;

        try {
            // 토스 페이먼츠의 /payments/confirm API 호출 (결제 승인)
            tossConfirmResult = tossClient.confirmPayment(
                command.getPaymentKey(),
                command.getOrderId(),
                command.getAmount()
            );

            // 프론트에서 결제 요청한 금액과, 토스에서 실제로 결제한 금액이 일치하는지 확인
            // 일치하지 않는다면 예외를 터트려서 토스 결제 취소
            paymentService.validatePaymentAmount(tossConfirmResult.getTotalAmount(), command.getAmount());

            // 토스 결제를 완료하고, 금액 검증을 통과하였으면 토스에서 응답받은 값을 이용하여 payment 객체 생성
            payment = paymentService.createPaymentFromConfirm(userId, tossConfirmResult);

            // 결제 완료 이벤트 발행
            eventPublisher.publishEvent(new PaymentCompletedEvent(
                payment.getUserId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getApprovedAt()
            ));

            // 포인트 충전 이벤트 발행
            eventPublisher.publishEvent(new PointChargeRequested(
                payment.getUserId(),
                payment.getAmount(),
                "CHARGE",
                payment.getOrderId()
            ));

            return PaymentApplicationMapper.toPaymentConfirmResult(payment);

        } catch (RestClientResponseException e) { // tossClient 에서 오류가 발생하였을 때,
            // Toss 오류 로그로 남김 (code + message)
            log.warn("Toss 결제 실패 - status: {}, body: {}", e.getRawStatusCode(), e.getResponseBodyAsString());
            throw new PaymentException(PaymentErrorCode.TOSS_ERROR);
        } catch (Exception e) {
            try {
                tossCancelResult = tossClient.cancelPayment(command.getPaymentKey(), "시스템 오류로 인한 결제 취소");

                if (payment != null) {
                    paymentService.failPayment(tossCancelResult);
                } else {
                    throw new PaymentException(PaymentErrorCode.PAYMENT_PROCESSING_FAILED);
                }
            } catch (Exception cancelEx) {
                log.error("토스 취소 실패 - 수동 처리 필요: paymentKey={}", command.getPaymentKey(), cancelEx);
            }
            throw new PaymentException(PaymentErrorCode.PAYMENT_PROCESSING_FAILED, e);
        }
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
