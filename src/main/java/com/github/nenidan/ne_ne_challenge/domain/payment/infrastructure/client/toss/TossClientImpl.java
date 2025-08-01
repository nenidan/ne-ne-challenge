package com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.client.toss;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.client.TossClient;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.TossClientResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;
import com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.client.dto.response.TossConfirmResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.client.toss.config.TossPaymentsConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TossClientImpl implements TossClient {

    private final RestClient restClient = RestClient.builder().build();
    private final TossPaymentsConfig tossPaymentsConfig;

    @Override
    public TossClientResult confirmPayment(String paymentKey, String orderId, int amount) {
        // 요청 바디 생성
        Map<String, Object> request = Map.of(
            "paymentKey", paymentKey,
            "orderId", orderId,
            "amount", amount
        );

        try {
            String url = tossPaymentsConfig.getBaseUrl() + "/payments/confirm";
            log.info("토스페이먼츠 confirm API 호출 - paymentKey: {}, orderId: {}", paymentKey, orderId);

            TossConfirmResponse response = restClient.post()
                .uri(url)
                .body(request)
                .header("Authorization", createAuthorizationHeader())
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(TossConfirmResponse.class);

            if (response == null) {
                throw new PaymentException(PaymentErrorCode.CONFIRM_FAILED);
            }

            log.info("토스페이먼츠 confirm API 성공 - status: {}", response.getStatus());

            return new TossClientResult(
                response.getPaymentKey(),
                response.getOrderId(),
                response.getStatus(),
                response.getMethod(),
                response.getOrderName(),
                response.getRequestedAt(),
                response.getApprovedAt(),
                response.getTotalAmount()
            );

        } catch (Exception e) {
            log.error("토스페이먼츠 confirm API 호출 실패", e);
            throw new PaymentException(PaymentErrorCode.CONFIRM_FAILED);
        }
    }

    private String createAuthorizationHeader() {
        String credentials = tossPaymentsConfig.getSecretKey() + ":";
        String encodedCredentials = Base64.getEncoder()
            .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        return "Basic " + encodedCredentials;
    }
}
