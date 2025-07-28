package com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response;

import lombok.Getter;

@Getter
public class ChallengeSuccessRateResponse {

    private int successRate;

    public ChallengeSuccessRateResponse(int successRate) {
        this.successRate = successRate;
    }
}
