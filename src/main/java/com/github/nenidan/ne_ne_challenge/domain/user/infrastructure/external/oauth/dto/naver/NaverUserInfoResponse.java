package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.oauth.dto.naver;

import lombok.Getter;

@Getter
public class NaverUserInfoResponse {

    private String resultcode;
    private String message;
    private Profile response;
}
