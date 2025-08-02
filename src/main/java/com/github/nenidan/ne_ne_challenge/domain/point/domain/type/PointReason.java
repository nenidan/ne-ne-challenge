package com.github.nenidan.ne_ne_challenge.domain.point.domain.type;

import java.util.Arrays;

import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PointReason {
    // 포인트 적립
    CHARGE("포인트 결제"),
    CHALLENGE_REWARD("챌린지 보상"),

    // 포인트 차감
    SHOP_PURCHASE("상점 구매"),
    CHALLENGE_ENTRY("챌린지 참가비");

    private final String description;

    // 포인트 증가 사유인지 체크
    public boolean isIncrease() {
        return this == CHARGE || this == CHALLENGE_REWARD;
    }

    // 포인트 감소 사유인지 체크
    public boolean isDecrease() {
        return this == SHOP_PURCHASE || this == CHALLENGE_ENTRY;
    }

    public static PointReason of(String reason) {
        return Arrays.stream(PointReason.values())
            .filter(r -> r.name().equalsIgnoreCase(reason))
            .findFirst()
            .orElseThrow(() -> new PointException(PointErrorCode.INVALID_POINT_REASON));
    }
}
