package com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointBalanceResponse {

    @Schema(description = "현재 포인트 잔액", example = "10000")
    private int balance;
}
