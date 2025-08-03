package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.oauth.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class Profile {
    private String nickname;

    @JsonProperty("profile_image_url")
    private String profileImageUrl;
}
