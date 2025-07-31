package com.github.nenidan.ne_ne_challenge.global.client.point;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PointClientCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import jakarta.annotation.PostConstruct;
import org.springframework.web.client.RestClientResponseException;

@Component
public class PointClientImpl implements PointClient {

    @Value("${external.base-url}")
    private String baseUrl;

    private RestClient restClient;

    @PostConstruct
    public void init() {
        restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    @Override
    public void createPointWallet(Long userId) {
        restClient.post()
            .uri(uriBuilder -> uriBuilder.path("/internal/points/wallet")
                .queryParam("userId", userId)
                .build()
            )
            .retrieve()
            .toBodilessEntity();
    }

    @Override
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
