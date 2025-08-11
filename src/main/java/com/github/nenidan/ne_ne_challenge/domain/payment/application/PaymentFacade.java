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

    /**
     * 결제 승인 및 포인트 충전 요청 처리
     * 1. 사용자 검증 -> 2. 토스 결제 승인 -> 3. 결제 정보 저장 -> 4. 포인트 충전 이벤트 발행
     */
    public PaymentConfirmResult confirmAndChargePoint(Long userId, PaymentConfirmCommand command) {

        boolean userVerified = false;
        boolean tossPaymentSucceeded = false;
        TossConfirmResult tossConfirmResult = null;
        Payment payment;

        try {
            // 유저 검증
            userClient.getUserById(userId);
            userVerified = true;

            // 토스 페이먼츠의 /payments/confirm API 호출 (결제 승인)
            tossConfirmResult = tossClient.confirmPayment(
                command.getPaymentKey(),
                command.getOrderId(),
                command.getAmount()
            );
            tossPaymentSucceeded = true;

            // 프론트에서 요청한 결제 금액과 토스에서 실제로 결제한 금액이 일치하는지 확인
            // 일치하지 않는다면 예외를 터트린다.
            // 일치한다면, 토스에서 응답받은 값을 이용하여 payment 객체 생성
            payment = paymentService.createPaymentWithValidation(
                userId,
                tossConfirmResult,
                command.getAmount()
            );

            // 결제 완료 이벤트 발행
            //            eventPublisher.publishEvent(new PaymentCompletedEvent(
            //                payment.getUserId(),
            //                payment.getOrderId(),
            //                payment.getAmount(),
            //                payment.getPaymentMethod(),
            //                payment.getApprovedAt()
            //            ));

            // 포인트 충전 이벤트 발행
            publishPointChargeEvent(payment);

            return PaymentApplicationMapper.toPaymentConfirmResult(payment);

        } catch (RestClientResponseException e) {
            // userClient, tossClient 에서 오류가 발생하였을 때,
            handleRestClientError(e, userVerified, tossPaymentSucceeded);
            return null;
        } catch (PaymentException e) {
            // 결제 검증 실패 시
            if (tossConfirmResult != null) {
                handlePaymentError(userId, tossConfirmResult);
            }
            throw e;
        }
    }

    /**
     * 결제 취소 처리
     * 1. 사용자 검증 -> 2. 결제 취소 가능 여부 확인 -> 3. 토스 결제 취소 -> 4. 결제 정보 업데이트 -> 5. 포인트 차감
     */
    public PaymentCancelResult cancelPayment(Long userId, String orderId, PaymentCancelCommand command) {

        // 사용자 검증
        userClient.getUserById(userId);

        // 결제 정보 조회 및 기본 검증 (통과 한다면 취소가 가능한 결제 내역이다.)
        Payment payment = paymentService.validatePaymentForCancel(userId, orderId);

        // 그러면 이제 토스 페이먼츠에 내 DB에 저장된 결제 내역에서 paymentKey와 cancelReason을 가지고 취소 요청을 보낸다.
        TossCancelResult tossCancelResult = tossClient.cancelPayment(payment.getPaymentKey(),
            command.getCancelReason());

        // 토스 결제 취소에 성공한다면, 결제의 상태를 CANCELED로 업데이트
        PaymentCancelResult result = paymentService.updatePaymentCancel(payment, tossCancelResult, command);

        // 토스에서 취소가 완료가 되었으면 이제 point 쪽에 취소(포인트를 다시 차감)하라고 요청을 보낸다.
        pointClient.cancelPoint(orderId);

        return result;
    }

    /**
     * 자신의 결제 내역 확인
     */
    public CursorResponse<PaymentSearchResult, Long> searchMyPayments(Long userId, PaymentSearchCommand command) {

        userClient.getUserById(userId);

        return paymentService.searchMyPayments(userId, command);
    }

    // ============================== private 헬퍼 메서드 ==============================

    private void publishPointChargeEvent(Payment payment) {
        try {
            eventPublisher.publishEvent(new PointChargeRequested(
                payment.getUserId(),
                payment.getAmount(),
                "CHARGE",
                payment.getOrderId()
            ));
        } catch (Exception e) {
            log.info("포인트 충전 이벤트 발행에 실패하였습니다.");
        }
    }

    private void handleRestClientError(RestClientResponseException e, boolean userVerified,
        boolean tossPaymentSucceeded) {
        if (!userVerified) {
            // 유저 검증 단계에서 실패 하였을 때
            throw new PaymentException(PaymentErrorCode.USER_NOT_FOUND, e);
        } else if (!tossPaymentSucceeded) {
            // 토스 결제 단계에서 실패 하였을 때
            throw new PaymentException(PaymentErrorCode.TOSS_ERROR, e);
        }
    }

    private void handlePaymentError(Long userId, TossConfirmResult tossConfirmResult) {
        try {
            Payment failPayment = paymentService.createFailPayment(userId, tossConfirmResult);

            TossCancelResult tossCancelResult = tossClient.cancelPayment(failPayment.getPaymentKey(),
                "시스템 오류로 인한 결제 취소");
        } catch (Exception ex) {
            // 토스 결제 취소마저 실패를 하였을 경우
            log.error("토스 결제 취소 실패 - 수동 처리 필요.");
            throw new PaymentException(PaymentErrorCode.PAYMENT_PROCESSING_FAILED, ex);
        }
    }
}
