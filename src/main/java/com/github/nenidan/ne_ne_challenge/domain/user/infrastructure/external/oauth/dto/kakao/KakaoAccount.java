package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.oauth.dto.kakao;

import lombok.Getter;

@Getter
public class KakaoAccount {
    private Profile profile;
    private String email;
    private String birthyear;
    private String birthday;

    public String getBirth() {
        return birthyear + birthday;
    }
}
