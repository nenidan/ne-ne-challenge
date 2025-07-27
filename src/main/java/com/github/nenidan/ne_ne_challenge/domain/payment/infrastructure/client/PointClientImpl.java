package com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.client.PointClient;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PointClientCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PointClientImpl implements PointClient {

    private final RestClient restClient = RestClient.builder()
        .baseUrl("http://localhost:8080/internal")
        .build();

    public void chargePoint(PointClientCommand pointClientCommand) {
        try {
            restClient.post()
                .uri("/points/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .body(pointClientCommand)
                .retrieve()
                .toBodilessEntity();
        } catch (RestClientResponseException e) {
            throw new PaymentException(PaymentErrorCode.POINT_CHARGE_FAILED);
        }
    }
}
