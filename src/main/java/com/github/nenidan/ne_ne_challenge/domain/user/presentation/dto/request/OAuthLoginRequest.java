package com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.type.Provider;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class OAuthLoginRequest {

    @Schema(description = "엑세스 토큰", example = "AAAAOVvOMx7AX87LYmWVWuw7xPz31acJpnivNp7vHc/0pyKlMT3/FnpOO8dvnM7K49j5QCWNC0LrlGY8qZ/XHBoaePg=")
    @NotBlank(message = "토큰은 필수입니다.")
    String token;

    @Schema(hidden = true, example = "naver")
    @Setter
    Provider provider;
}
