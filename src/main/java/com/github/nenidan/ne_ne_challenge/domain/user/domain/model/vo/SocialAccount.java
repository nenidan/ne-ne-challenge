package com.github.nenidan.ne_ne_challenge.domain.user.domain.model.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialAccount {
    private final String kakaoId;
    private final String naverId;
    private final String googleId;

    public static SocialAccount of(String kakaoId, String naverId, String googleId) {
        return new SocialAccount(kakaoId, naverId, googleId);
    }

    public static SocialAccount fromKakao(String kakaoId) {
        return new SocialAccount(kakaoId, null, null);
    }

    public static SocialAccount fromNaver(String naverId) {
        return new SocialAccount(null, naverId, null);
    }

    public static SocialAccount fromGoogle(String googleId) {
        return new SocialAccount(null, null, googleId);
    }
}
