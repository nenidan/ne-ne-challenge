package com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.type.Provider;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class OAuthLoginRequest {

    @NotBlank(message = "토큰은 필수입니다.")
    String token;

    @Setter
    Provider provider;
}
