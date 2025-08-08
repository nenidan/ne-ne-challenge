package com.github.nenidan.ne_ne_challenge.domain.payment.application.handler;

import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.domain.payment.domain.event.PointChargeRequested;
import com.github.nenidan.ne_ne_challenge.global.client.point.PointClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PointChargeEventHandler {

    private final PointClient pointClient;

    @EventListener
    @Async
    @Retryable(
        retryFor = {Exception.class}, // 모든 예외에 대하여 재시도
        noRetryFor = {IllegalStateException.class}, // 파라미터 오류는 재시도 해도 똑같이 오류가 나니까 재시도에서 제외
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000) // 1초 기다리고 재시도
    )
    public void handlePointCharge(PointChargeRequested event) {
        log.info("포인트 충전 시도: orderId = {}", event.getOrderId());
        pointClient.chargePoint(
            event.getUserId(),
            event.getAmount(),
            event.getReason(),
            event.getOrderId()
        );
        log.info("포인트 충전 성공: orderId = {}", event.getOrderId());
    }

    @Recover
    public void recover(Exception e, PointChargeRequested event) {
        log.error("포인트 충전 최종 실패 - 수동 처리 필요: orderId = {}", event.getOrderId(), e);
    }
}
