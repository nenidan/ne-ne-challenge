package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.oauth.dto.kakao;

import lombok.Getter;

@Getter
public class KakaoUserInfoRequest {
    private String[] propertyKeys;

    public KakaoUserInfoRequest(String... propertyKeys) {
        this.propertyKeys = propertyKeys;
    }
}
