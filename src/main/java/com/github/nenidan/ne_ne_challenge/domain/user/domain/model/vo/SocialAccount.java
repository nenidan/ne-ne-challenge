package com.github.nenidan.ne_ne_challenge.domain.user.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SocialAccount {
    private final Long kakaoId;
    private final String naverId;
    private final String googleId;
}
