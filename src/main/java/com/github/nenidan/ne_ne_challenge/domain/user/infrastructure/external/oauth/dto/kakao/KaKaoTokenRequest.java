package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.oauth.dto.kakao;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KaKaoTokenRequest {

    private String grantType;

    private String clientId;

    private String redirectUri;

    private String code;

}
