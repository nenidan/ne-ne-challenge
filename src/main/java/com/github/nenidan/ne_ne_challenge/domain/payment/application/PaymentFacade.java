package com.github.nenidan.ne_ne_challenge.domain.payment.application;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
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
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.event.PaymentCompletedEvent;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;
import com.github.nenidan.ne_ne_challenge.global.client.point.PointClient;
import com.github.nenidan.ne_ne_challenge.global.client.user.UserClient;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentFacade {

    private final PaymentService paymentService;
    private final UserClient userClient;
    private final TossClient tossClient;
    private final ApplicationEventPublisher eventPublisher;
    private final PointClient pointClient;

    public PaymentPrepareResult preparePayment(Long userId, PaymentPrepareCommand paymentPrepareCommand) {
        // 유저 검증
        userClient.getUserById(userId);

        return paymentService.preparePayment(userId, paymentPrepareCommand.getAmount());
    }

    /**
     * 결제 승인 및 포인트 충전 요청 처리
     * 1. 사용자 검증 -> 2. 토스 결제 승인 -> 3. Payment 객체를 DONE 으로 변경 -> 4. 포인트 충전, 알림 이벤트 발행
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
            payment = paymentService.markAsSuccess(
                tossConfirmResult,
                command.getAmount()
            );

            // 포인트 충전 및 알림 전송 이벤트 발행
            publishPaymentCompletedEvent(payment);

            return PaymentApplicationMapper.toPaymentConfirmResult(payment);

        } catch (RestClientResponseException e) {
            // RestClient 에서 오류가 발생하였을 때,

            // 1. 토스 결제 요청 하기 전 실행된 prepare API 에서 저장된 Payment 객체를 fail 상태로 바꾼다.
            paymentService.markAsFailed(
                command.getOrderId(),
                command.getPaymentKey()
            );

            // 2. userClient 오류인지, tossClient 오류인지 확인 후에, 적절한 오류를 던져준다.
            handleRestClientError(e, userVerified, tossPaymentSucceeded);

            // 실행되지 않음, 컴파일 에러 방지용
            return null;
        } catch (Exception e) {
            // 어쩔 수 없이 발생하는 모든 예외 상황

            // 1. Payment 객체를 fail 상태로 수정
            paymentService.markAsFailed(
                command.getOrderId(),
                command.getPaymentKey()
            );

            // 2. 토스 결제 승인을 한 상태로 예외가 발생하면, 토스 결제를 취소해줘야한다.
            if (tossConfirmResult != null) {
                cancelToss(tossConfirmResult);
            }

            throw new PaymentException(PaymentErrorCode.PAYMENT_PROCESSING_FAILED, e);
        }
    }

    /**
     * 결제 취소 처리
     * 1. 사용자 검증 -> 2. 결제 취소 가능 여부 확인 후 결제 취소 -> 3. 토스 결제 취소 -> 4. 포인트 차감
     */
    public PaymentCancelResult cancelPayment(Long userId, String orderId, PaymentCancelCommand command) {

        boolean userVerified = false;
        boolean paymentCanceled = false;
        boolean tossCanceled = false;
        PaymentCancelResult result = null;

        try {
            // 1. 사용자 검증
            userClient.getUserById(userId);
            userVerified = true;

            // 2. DB를 취소 처리
            result = paymentService.cancelPayment(userId, orderId, command);
            paymentCanceled = true;

            // 3. 토스 페이에 결제 취소 요청
            TossCancelResult tossCancelResult = tossClient.cancelPayment(
                result.getPaymentKey(),
                result.getCancelReason()
            );
            tossCanceled = true;

            // 4. 포인트 차감
            pointClient.cancelPoint(orderId);

            return result;

        } catch (RestClientResponseException e) {
            handleRestClientError(orderId, e, userVerified, paymentCanceled, tossCanceled);
            return null;
        } catch (Exception e) {
            handleUnexpectedError(orderId, e, tossCanceled);
            return null;
        }
    }

    /**
     * 자신의 결제 내역 확인
     */
    public CursorResponse<PaymentSearchResult, Long> searchMyPayments(Long userId, PaymentSearchCommand command) {

        userClient.getUserById(userId);

        return paymentService.searchMyPayments(userId, command);
    }

    // ============================== private 헬퍼 메서드 ==============================

    // 포인트 충전이 결제에 장애를 전파하지 않기 위함
    private void publishPaymentCompletedEvent(Payment payment) {
        try {
            eventPublisher.publishEvent(new PaymentCompletedEvent(
                payment.getUserId(),
                payment.getAmount().getValue(),
                "CHARGE",
                payment.getOrderId().getValue()
            ));
        } catch (Exception e) {
            log.info("포인트 충전 이벤트 발행 실패 - 수동 처리 필요: orderId = {}", payment.getOrderId());
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

    private void cancelToss(TossConfirmResult tossConfirmResult) {
        try {
            // 토스 결제 취소 요청
            tossClient.cancelPayment(
                tossConfirmResult.getPaymentKey(),
                "시스템 오류로 인한 결제 취소"
            );
        } catch (Exception ex) {
            // 토스 결제 취소마저 실패를 하였을 경우
            log.error("토스 결제 취소 실패 - 수동 처리 필요.");
            throw new PaymentException(PaymentErrorCode.PAYMENT_PROCESSING_FAILED, ex);
        }
    }

    // 결제 취소 중 예상치 못한 예외가 발생하였을 때
    private void handleUnexpectedError(String orderId, Exception e, boolean tossCanceled) {
        if (!tossCanceled) {
            paymentService.rollbackCancel(orderId);
        } else {
            throw new PaymentException(PaymentErrorCode.PAYMENT_CANCEL_FAILED, e);
        }
    }

    // 결제 취소 중 restClient 예외가 발생하였을 때
    private void handleRestClientError(String orderId, RestClientResponseException e, boolean userVerified,
        boolean paymentCanceled,
        boolean tossCanceled) {
        if (!userVerified) {
            throw new PaymentException(PaymentErrorCode.USER_NOT_FOUND, e);
        } else if (!paymentCanceled) {
            throw new PaymentException(PaymentErrorCode.PAYMENT_CANCEL_FAILED, e);
        } else if (!tossCanceled) {
            paymentService.rollbackCancel(orderId);
            throw new PaymentException(PaymentErrorCode.TOSS_ERROR, e);
        } else {
            throw new PaymentException(PaymentErrorCode.PAYMENT_CANCEL_FAILED, e);
        }
    }

}
