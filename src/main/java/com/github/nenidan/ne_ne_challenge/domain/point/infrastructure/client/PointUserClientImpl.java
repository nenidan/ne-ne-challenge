package com.github.nenidan.ne_ne_challenge.domain.point.infrastructure.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;
import com.github.nenidan.ne_ne_challenge.domain.point.application.client.UserClient;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.UserIdResult;
import com.github.nenidan.ne_ne_challenge.domain.point.infrastructure.client.dto.response.UserIdResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class PointUserClientImpl implements UserClient {

    private final RestClient restClient = RestClient.builder()
        .baseUrl("http://localhost:8080/internal")
        .build();

    @Override
    public UserIdResult getUser(Long userId) {
        try {
            UserIdResponse response = restClient.get()
                .uri("/profiles/{userId}", userId)
                .retrieve()
                .body(UserIdResponse.class);
            return new UserIdResult(response.getId());
        } catch (Exception e) {
            log.error("유저 조회 실패 userId={}, message={}", userId, e.getMessage(), e);
            throw new PaymentException(PaymentErrorCode.USER_NOT_FOUND);
        }
    }
}
