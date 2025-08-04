package com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.type;

import java.util.Arrays;

public enum Provider {
    KAKAO, NAVER, GOOGLE;

    public static Provider of(String provider) {
        return Arrays.stream(Provider.values())
                .filter(r -> r.name().equalsIgnoreCase(provider))
                .findFirst().orElse(null);
    }
}
