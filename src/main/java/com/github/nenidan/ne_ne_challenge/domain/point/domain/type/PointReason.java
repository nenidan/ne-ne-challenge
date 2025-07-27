package com.github.nenidan.ne_ne_challenge.domain.point.domain.type;

import java.util.Arrays;

import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointException;

public enum PointReason {
    CHARGE;

    public static PointReason of(String reason) {
        return Arrays.stream(PointReason.values())
            .filter(r -> r.name().equalsIgnoreCase(reason))
            .findFirst()
            .orElseThrow(() -> new PointException(PointErrorCode.INVALID_POINT_REASON));
    }
}
