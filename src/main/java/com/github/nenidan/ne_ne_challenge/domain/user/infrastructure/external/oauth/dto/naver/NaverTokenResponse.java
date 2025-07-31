package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.oauth.dto.naver;

import lombok.Getter;

@Getter
public class NaverTokenResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Integer expiresIn;
    private String error;
    private String errorDescription;
}
