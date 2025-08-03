package com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.dto;

import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.type.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class OAuthUserInfo {

    private Provider provider;
    private String providerId;
    private String email;
    private String nickname;
    private LocalDate birth;
    private String imageUrl;
}
