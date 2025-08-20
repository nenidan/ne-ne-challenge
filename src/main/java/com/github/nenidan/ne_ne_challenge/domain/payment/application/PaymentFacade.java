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

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
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

        try {
            // 1. 사용자 검증
            userClient.getUserById(userId);
            userVerified = true;

            // 2. 토스 결제 승인
            tossConfirmResult = tossClient.confirmPayment(
                command.getPaymentKey(), command.getOrderId(), command.getAmount());
            tossPaymentSucceeded = true;

            // 3. Payment 성공 처리
            Payment payment = paymentService.markAsSuccess(tossConfirmResult, command.getAmount());

            // 4. 포인트 충전 및 알림 전송 이벤트 발행
            publishPaymentCompletedEvent(payment);

            return PaymentApplicationMapper.toPaymentConfirmResult(payment);

        } catch (CallNotPermittedException e) {

            log.error("토스 서킷 브레이커 OPEN: {}", e.getMessage());

            handleConfirmCircuitBreakerError(command);

            throw new PaymentException(PaymentErrorCode.TOSS_SERVICE_UNAVAILABLE, e);

        } catch (RestClientResponseException e) {

            log.error("외부 클라이언트 오류: {}", e.getMessage());

            handleConfirmRestClientError(e, command, userVerified, tossPaymentSucceeded);

            return null;

        } catch (Exception e) {

            log.error("예상치 못한 오류: {}", e.getMessage());

            handleConfirmUnknownError(command, tossConfirmResult);

            throw new PaymentException(PaymentErrorCode.PAYMENT_PROCESSING_FAILED, e);
        }
    }

    /**
     * 결제 취소 처리
     * 1. 사용자 검증 -> 2. 결제 취소 가능 여부 확인 -> 3. 포인트 차감 -> 4. DB 취소 -> 5. 토스 취소
     */
    public PaymentCancelResult cancelPayment(Long userId, String orderId, PaymentCancelCommand command) {
        boolean userVerified = false;
        boolean pointCanceled = false;
        boolean paymentCanceled = false;
        boolean tossCanceled = false;
        PaymentCancelResult result = null;

        try {
            // 1. 사용자 검증
            userClient.getUserById(userId);
            userVerified = true;

            // 2. 취소 가능한 결제인지 확인
            Payment paymentForCancel = paymentService.getPaymentForCancel(userId, orderId);

            // 3. 포인트 차감
            pointClient.cancelPoint(orderId);
            pointCanceled = true;

            // 4. DB 취소 처리
            result = paymentService.cancelPayment(paymentForCancel, command);
            paymentCanceled = true;

            // 5. 토스 취소
            TossCancelResult tossCancelResult = tossClient.cancelPayment(
                paymentForCancel.getPaymentKey().getValue(), command.getCancelReason());
            tossCanceled = true;

            return result;

        } catch (CallNotPermittedException e) {

            log.error("토스 취소 서킷 브레이커 OPEN: orderId={}", orderId, e);

            handleCancelCircuitBreakerError(userId, orderId, result);

            throw new PaymentException(PaymentErrorCode.TOSS_SERVICE_UNAVAILABLE, e);

        } catch (RestClientResponseException e) {

            log.error("취소 중 외부 클라이언트 오류: orderId={}", orderId, e);

            handleCancelRestClientError(userId, orderId, result, e, userVerified, pointCanceled, paymentCanceled,
                tossCanceled);

            return null;

        } catch (Exception e) {

            log.error("취소 중 예상치 못한 오류: orderId={}", orderId, e);

            handleCancelUnexpectedError(userId, orderId, result, e, pointCanceled, paymentCanceled, tossCanceled);

            return null;
        }
    }

    public CursorResponse<PaymentSearchResult, Long> searchMyPayments(Long userId, PaymentSearchCommand command) {

        userClient.getUserById(userId);

        return paymentService.searchMyPayments(userId, command);
    }

    // ================================ 결제 승인 헬퍼 메서드 ================================

    private void handleConfirmCircuitBreakerError(PaymentConfirmCommand command) {
        paymentService.markAsFailed(command.getOrderId(), command.getPaymentKey());
    }

    private void handleConfirmRestClientError(RestClientResponseException e, PaymentConfirmCommand command,
        boolean userVerified, boolean tossPaymentSucceeded) {
        paymentService.markAsFailed(command.getOrderId(), command.getPaymentKey());

        if (!userVerified) {
            throw new PaymentException(PaymentErrorCode.USER_NOT_FOUND, e);
        } else if (!tossPaymentSucceeded) {
            throw new PaymentException(PaymentErrorCode.TOSS_ERROR, e);
        }
    }

    private void handleConfirmUnknownError(PaymentConfirmCommand command, TossConfirmResult tossConfirmResult) {
        paymentService.markAsFailed(command.getOrderId(), command.getPaymentKey());

        if (tossConfirmResult != null) {
            cancelToss(tossConfirmResult);
        }
    }

    private void cancelToss(TossConfirmResult tossConfirmResult) {
        try {
            tossClient.cancelPayment(tossConfirmResult.getPaymentKey(), "시스템 오류로 인한 결제 취소");
        } catch (Exception ex) {
            log.error("토스 결제 취소 실패 - 수동 처리 필요");
            throw new PaymentException(PaymentErrorCode.PAYMENT_PROCESSING_FAILED, ex);
        }
    }

    // ================================ 결제 취소 헬퍼 메서드 ================================

    private void handleCancelCircuitBreakerError(Long userId, String orderId, PaymentCancelResult result) {
        try {
            log.info("취소 서킷 브레이커 보상 시작: orderId={}", orderId);

            paymentService.rollbackCancel(orderId);
            pointClient.increasePoint(userId, result.getRefundAmount(), "RESTORE_POINT");

            log.info("취소 서킷 브레이커 보상 완료: orderId={}", orderId);
        } catch (Exception e) {
            log.error("보상 실패 - 수동 처리 필요: orderId={}", orderId, e);
        }
    }

    private void handleCancelRestClientError(Long userId, String orderId, PaymentCancelResult result,
        RestClientResponseException e, boolean userVerified,
        boolean pointCanceled, boolean paymentCanceled, boolean tossCanceled) {
        try {
            log.info("취소 RestClient 오류 보상 시작: orderId={}", orderId);

            if (paymentCanceled && !tossCanceled) {
                paymentService.rollbackCancel(orderId);
            }

            if (pointCanceled && (!paymentCanceled || !tossCanceled)) {
                pointClient.increasePoint(
                    userId,
                    result.getRefundAmount(),
                    "RESTORE_POINT"
                );
            }

            log.info("취소 RestClient 오류 보상 완료: orderId={}", orderId);
        } catch (Exception compensationEx) {
            log.error("보상 실패 - 수동 처리 필요: orderId={}", orderId, compensationEx);
        }

        // 적절한 예외 던지기
        if (!userVerified) {
            throw new PaymentException(PaymentErrorCode.USER_NOT_FOUND, e);
        } else if (!paymentCanceled) {
            throw new PaymentException(PaymentErrorCode.PAYMENT_CANCEL_FAILED, e);
        } else if (!tossCanceled) {
            throw new PaymentException(PaymentErrorCode.TOSS_ERROR, e);
        } else {
            throw new PaymentException(PaymentErrorCode.PAYMENT_CANCEL_FAILED, e);
        }
    }

    private void handleCancelUnexpectedError(Long userId, String orderId, PaymentCancelResult result,
        Exception e, boolean pointCanceled,
        boolean paymentCanceled, boolean tossCanceled) {
        try {
            log.info("취소 예상치 못한 오류 보상 시작: orderId={}", orderId);

            if (paymentCanceled && !tossCanceled) {
                paymentService.rollbackCancel(orderId);
            }

            if (pointCanceled && (!paymentCanceled || !tossCanceled)) {
                pointClient.increasePoint(userId, result.getRefundAmount(), "RESTORE_POINT");
            }

            log.info("취소 예상치 못한 오류 보상 완료: orderId={}", orderId);
        } catch (Exception compensationEx) {
            log.error("보상 실패 - 수동 처리 필요: orderId={}", orderId, compensationEx);
        }

        throw new PaymentException(PaymentErrorCode.PAYMENT_CANCEL_FAILED, e);
    }

    // ================================ 공통 헬퍼 메서드 ================================

    private void publishPaymentCompletedEvent(Payment payment) {
        try {
            eventPublisher.publishEvent(
                new PaymentCompletedEvent(
                payment.getUserId(),
                payment.getAmount().getValue(),
                "CHARGE",
                payment.getOrderId().getValue()
                )
            );
        } catch (Exception e) {
            log.info("포인트 충전 이벤트 발행 실패 - 수동 처리 필요: orderId={}", payment.getOrderId());
        }
    }
}
