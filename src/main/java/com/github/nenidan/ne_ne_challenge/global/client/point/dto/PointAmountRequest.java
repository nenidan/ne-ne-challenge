package com.github.nenidan.ne_ne_challenge.global.client.point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 포인트 관련 작업(충전, 증가, 감소)를 사용하는 요청 DTO 입니다.
 */
@AllArgsConstructor
@Getter
public class PointAmountRequest {
    private int amount;
    private String reason;
}
