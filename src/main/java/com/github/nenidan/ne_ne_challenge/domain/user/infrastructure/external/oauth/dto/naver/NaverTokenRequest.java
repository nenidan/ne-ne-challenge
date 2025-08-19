package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.oauth.dto.naver;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class NaverTokenRequest {

    private String grantType;

    private String clientId;

    private String clientSecret;

    private String code;

    private String state;

}
