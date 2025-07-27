package com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.client.UserClient;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.UserClientResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;
import com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.client.dto.response.UserClientResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {

    private final RestClient restClient = RestClient.builder()
        .baseUrl("http://localhost:8080/internal")
        .build();

    @Override
    public UserClientResult getUser(Long userId) {
        try {
            UserClientResponse response = restClient.get()
                .uri("/profiles/{userId}", userId)
                .retrieve()
                .body(UserClientResponse.class);
            return new UserClientResult(response.getId());
        } catch (Exception e) {
            throw new PaymentException(PaymentErrorCode.USER_NOT_FOUND);
        }
    }
}
