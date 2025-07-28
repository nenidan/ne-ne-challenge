package com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response;

import lombok.Getter;

@Getter
public class ChallengeSuccessRateResponse {

    public ChallengeSuccessRateResponse(int successRate) {
        this.successRate = successRate;
    }

    private int successRate;
}
