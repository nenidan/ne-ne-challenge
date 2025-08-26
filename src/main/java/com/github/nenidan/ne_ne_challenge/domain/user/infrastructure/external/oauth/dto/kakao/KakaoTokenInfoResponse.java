package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.oauth.dto.kakao;

import lombok.Getter;

@Getter
public class KakaoTokenInfoResponse {
    private Long id;
    private Integer expiresIn;
    private Integer appId;
}
