package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.oauth.dto.naver;

import lombok.Getter;

@Getter
public class Profile {
    private String id;
    private String email;
    private String nickname;
    private String birthyear;
    private String birthday;
    private String profileImage;
}
