package com.github.nenidan.ne_ne_challenge.global.client.point;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.github.nenidan.ne_ne_challenge.global.client.point.dto.PointAmountRequest;
import com.github.nenidan.ne_ne_challenge.global.client.point.dto.PointChargeRequest;
import com.github.nenidan.ne_ne_challenge.global.client.point.dto.PointRefundRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PointClientImpl implements PointClient {

    private final RestClient restClient;

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

    @Override
    public void refundPoints(List<Long> userList, int amount) {

        PointRefundRequest pointRefundRequest = new PointRefundRequest(userList, amount);

        restClient.post()
            .uri("/internal/points/refund")
            .contentType(MediaType.APPLICATION_JSON)
            .body(pointRefundRequest)
            .retrieve()
            .toBodilessEntity();
    }
}
