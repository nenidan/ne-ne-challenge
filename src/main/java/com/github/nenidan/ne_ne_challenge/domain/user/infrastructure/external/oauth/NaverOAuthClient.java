package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.oauth;

import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.OAuthClient;
import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.dto.OAuthUserInfo;
import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.type.Provider;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.oauth.dto.naver.NaverTokenRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.oauth.dto.naver.NaverTokenResponse;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.oauth.dto.naver.NaverUserInfoResponse;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.oauth.dto.naver.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class NaverOAuthClient implements OAuthClient {

    private final RestClient oauthRestClient;

    @Value("${oauth.naver.client-id}")
    private String clientId;

    @Value("${oauth.naver.client-secret}")
    private String clientSecret;

    private static final String GRANT_TYPE = "authorization_code";

    @Override
    public Provider getProvider() {
        return Provider.NAVER;
    }

    @Override
    public OAuthUserInfo getOAuthUserInfo(String token) {

//        NaverTokenRequest tokenReq = new NaverTokenRequest(
//                clientId,
//                clientSecret,
//                GRANT_TYPE,
//                authorizationCode,
//                state
//        );
//
//        NaverTokenResponse tokenRes = oauthRestClient.post()
//                .uri("https://nid.naver.com/oauth2.0/token")
//                .body(tokenReq)
//                .retrieve()
//                .body(NaverTokenResponse.class);

        NaverUserInfoResponse userInfoResponse = Objects.requireNonNull(oauthRestClient.get()
                .uri("https://openapi.naver.com/v1/nid/me")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(NaverUserInfoResponse.class));

        Profile profile = userInfoResponse.getResponse();


        return new OAuthUserInfo(
                Provider.NAVER,
                profile.getId(),
                profile.getEmail(),
                profile.getNickname(),
                LocalDate.parse(profile.getBirthyear() + "-" + profile.getBirthday()),
                profile.getProfileImage()
        );
    }
}
