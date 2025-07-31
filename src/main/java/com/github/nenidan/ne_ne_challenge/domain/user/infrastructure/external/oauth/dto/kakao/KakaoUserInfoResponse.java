package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.oauth.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoUserInfoResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;
}
