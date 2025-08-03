package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.oauth;

import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.OAuthClient;
import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.dto.OAuthUserInfo;
import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.type.Provider;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.oauth.dto.kakao.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class KakaoOAuthClient implements OAuthClient {

    private final RestClient oauthRestClient;

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Value("${oauth.kakao.redirect-uri}")
    private String redirectUri;

    private static final String GRANT_TYPE = "authorization_code";

    @Override
    public Provider getProvider() {
        return Provider.KAKAO;
    }

    @Override
    public OAuthUserInfo getOAuthUserInfo(String token) {

//        KaKaoTokenRequest tokenReq = new KaKaoTokenRequest(
//                GRANT_TYPE,
//                clientId,
//                redirectUri,
//                authorizationCode
//        );
//
//        KakaoTokenResponse tokenRes = oauthRestClient.post()
//                .uri("https://kauth.kakao.com/oauth/token")
//                .body(tokenReq)
//                .retrieve()
//                .body(KakaoTokenResponse.class);

        KakaoTokenInfoResponse tokenInfo = oauthRestClient.get()
                .uri("https://kapi.kakao.com/v1/user/access_token_info")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(KakaoTokenInfoResponse.class);

        KakaoUserInfoResponse userInfo = oauthRestClient.post()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(KakaoUserInfoResponse.class);

        return new OAuthUserInfo(
                Provider.KAKAO,
                Objects.requireNonNull(tokenInfo).getId().toString(),
                Objects.requireNonNull(userInfo).getKakaoAccount().getEmail(),
                userInfo.getKakaoAccount().getProfile().getNickname(),
                null,
                userInfo.getKakaoAccount().getProfile().getProfileImageUrl()
        );
    }
}
