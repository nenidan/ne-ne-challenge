package com.github.nenidan.ne_ne_challenge.domain.user.application.dto;

import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.type.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthLoginCommand {

    String token;

    Provider provider;
}
