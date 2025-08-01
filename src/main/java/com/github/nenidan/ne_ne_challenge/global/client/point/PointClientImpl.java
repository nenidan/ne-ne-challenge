package com.github.nenidan.ne_ne_challenge.global.client.point;

import com.github.nenidan.ne_ne_challenge.global.client.point.dto.PointAmountRequest;
import com.github.nenidan.ne_ne_challenge.global.client.point.dto.PointChargeRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import jakarta.annotation.PostConstruct;

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
            .uri("/internal/points/{userId}/wallet", userId)
            .retrieve()
            .toBodilessEntity();
    }

    @Override
    public void chargePoint(Long userId, int amount, String reason, String orderId) {

        PointChargeRequest pointChargeRequest = new PointChargeRequest(amount, reason, orderId);

        restClient.post()
            .uri("/internal/points/{userId}/charge", userId)
            .contentType(MediaType.APPLICATION_JSON)
            .body(pointChargeRequest)
            .retrieve()
            .toBodilessEntity();
    }

    @Override
    public PointBalanceResponse getMyBalance(Long userId) {

        return restClient.get()
            .uri("/internal/points/{userId}", userId)
            .retrieve()
            .body(PointBalanceResponse.class);
    }

    @Override
    public void increasePoint(Long userId, int amount, String reason) {

        PointAmountRequest pointAmountRequest = new PointAmountRequest(amount, reason);

        restClient.post()
            .uri("/internal/points/{userId}/increase", userId)
            .contentType(MediaType.APPLICATION_JSON)
            .body(pointAmountRequest)
            .retrieve()
            .toBodilessEntity();
    }

    @Override
    public void decreasePoint(Long userId, int amount, String reason) {

        PointAmountRequest pointAmountRequest = new PointAmountRequest(amount, reason);

        restClient.post()
            .uri("/internal/points/{userId}/decrease", userId)
            .contentType(MediaType.APPLICATION_JSON)
            .body(pointAmountRequest)
            .retrieve()
            .toBodilessEntity();
    }

    @Override
    public void cancelPoint(String orderId) {

        restClient.delete()
            .uri("/internal/points/{orderId}", orderId)
            .retrieve()
            .toBodilessEntity();
    }
}
