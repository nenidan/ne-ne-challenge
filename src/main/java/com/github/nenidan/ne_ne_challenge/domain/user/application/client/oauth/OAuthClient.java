package com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth;

import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.dto.OAuthUserInfo;
import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.type.Provider;

public interface OAuthClient {

    Provider getProvider();

    OAuthUserInfo getOAuthUserInfo(String token);
}
