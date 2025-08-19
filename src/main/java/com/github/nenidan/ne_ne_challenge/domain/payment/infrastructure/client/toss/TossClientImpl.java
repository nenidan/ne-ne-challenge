package com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.client.toss;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.client.TossClient;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.TossCancelResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.TossConfirmResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.client.dto.request.TossCancelRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.client.dto.request.TossConfirmRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.client.dto.response.TossCancelResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.client.dto.response.TossConfirmResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.client.toss.config.TossPaymentsConfig;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TossClientImpl implements TossClient {

    @Qualifier("tossRestClient")
    private final RestClient tossRestClient;
    private final TossPaymentsConfig tossPaymentsConfig;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final RetryRegistry retryRegistry;

    @Override
    public TossConfirmResult confirmPayment(String paymentKey, String orderId, int amount) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(
            "tossPayments");
        Retry retry = retryRegistry.retry("tossPayments");

        // 1. callTossConfirm 메서드를 retry로 감싸서 실행해라
        // 2. retry를 circuitBreaker로 감싸서 실행해라
        return circuitBreaker.executeSupplier(() ->
            retry.executeSupplier(() ->
                callTossConfirm(paymentKey, orderId, amount)
            )
        );
    }

    @Override
    public TossCancelResult cancelPayment(String paymentKey, String cancelReason) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("tossPayments");
        Retry retry = retryRegistry.retry("tossPayments");

        return circuitBreaker.executeSupplier(() ->
            retry.executeSupplier(() ->
                callTossCancel(paymentKey, cancelReason)
            )
        );
    }

    private TossConfirmResult callTossConfirm(String paymentKey, String orderId, int amount) {
        // 요청 바디 생성
        TossConfirmRequest tossConfirmRequest = new TossConfirmRequest(
            paymentKey,
            orderId,
            amount
        );

        log.info("토스페이먼츠 confirm API 호출 - paymentKey: {}, orderId: {}", paymentKey, orderId);

        TossConfirmResponse response = tossRestClient.post()
            .uri(tossPaymentsConfig.getBaseUrl() + "/payments/confirm")
            .body(tossConfirmRequest)
            .header("Authorization", createAuthorizationHeader())
            .header("Idempotency-Key", createConfirmIdempotencyKey(orderId))
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(TossConfirmResponse.class);

        log.info("토스페이먼츠 confirm API 성공 - status: {}", response.getStatus());

        return new TossConfirmResult(
            response.getPaymentKey(),
            response.getOrderId(),
            response.getStatus(),
            response.getMethod(),
            response.getRequestedAt(),
            response.getApprovedAt(),
            response.getTotalAmount()
        );
    }

    private TossCancelResult callTossCancel(String paymentKey, String cancelReason) {
        TossCancelRequest tossCancelRequest = new TossCancelRequest(cancelReason);

        log.info("토스페이먼츠 cancel API 호출 - paymentKey: {}, orderId: {}", paymentKey, cancelReason);

        TossCancelResponse tossCancelResponse = tossRestClient.post()
            .uri(tossPaymentsConfig.getBaseUrl() + "/payments/{paymentKey}/cancel", paymentKey)
            .body(tossCancelRequest)
            .header("Authorization", createAuthorizationHeader())
            .header("Idempotency-Key", createCancelIdempotencyKey(paymentKey))
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(TossCancelResponse.class);

        return new TossCancelResult(
            tossCancelResponse.getOrderId(),
            tossCancelResponse.getStatus(),
            tossCancelResponse.getTotalAmount(),
            tossCancelResponse.getApprovedAt()
        );
    }

    private String createAuthorizationHeader() {
        String credentials = tossPaymentsConfig.getSecretKey() + ":";
        String encodedCredentials = Base64.getEncoder()
            .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        return "Basic " + encodedCredentials;
    }

    private String createConfirmIdempotencyKey(String orderId) {
        return "confirm" + orderId;
    }

    private String createCancelIdempotencyKey(String paymentKey) {
        return "cancel" + paymentKey;
    }
}
