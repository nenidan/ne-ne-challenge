package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChallengeSuccessRateResponse {

    @Schema(description = "현재까지의 인증 성공률(%)", example = "85")
    private int successRate;
}
