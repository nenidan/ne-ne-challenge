package com.github.nenidan.ne_ne_challenge.domain.user.application;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nenidan.ne_ne_challenge.domain.user.application.service.CachedUserService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.OAuthClient;
import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.OAuthClientFactory;
import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.dto.OAuthUserInfo;
import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.JoinCommand;
import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.LoginCommand;
import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.OAuthLoginCommand;
import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.UpdateProfileCommand;
import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.UserResult;
import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.UserWithTokenResult;
import com.github.nenidan.ne_ne_challenge.domain.user.application.mapper.UserMapper;
import com.github.nenidan.ne_ne_challenge.domain.user.application.service.JwtTokenProvider;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.User;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.service.UserService;
import com.github.nenidan.ne_ne_challenge.global.client.point.PointClient;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class UserFacade {
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final CachedUserService cachedUserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PointClient pointClient;
    private final OAuthClientFactory oauthClientFactory;

    public UserWithTokenResult join(JoinCommand joinCommand) {

        User user = UserMapper.toDomain(joinCommand);

        User savedUser = userService.join(user);

        pointClient.createPointWallet(savedUser.getId().getValue());

        return new UserWithTokenResult(
                UserMapper.toDto(savedUser),
                jwtTokenProvider.createAuthHeaders(savedUser)
        );
    }

    public UserWithTokenResult login(LoginCommand loginCommand) {

        User user = userService.login(loginCommand.getEmail(), loginCommand.getPassword());

        return new UserWithTokenResult(
                UserMapper.toDto(user),
                jwtTokenProvider.createAuthHeaders(user)
        );
    }

    public UserResult getProfile(Long id) {
        return UserMapper.toDto(userService.getProfile(id));
    }

    public List<UserResult> getProfiles() {
        return userService.getProfiles().stream().map(UserMapper::toDto).toList();
    }

    public CursorResponse<UserResult, String> searchProfiles(String cursor, int size, String keyword) {

        List<UserResult> userResultList;

        if(isDefaultSearchRequest(cursor, size, keyword)) {
            Object cached = cachedUserService.searchProfiles();
            userResultList = objectMapper.convertValue(
                    cached, new TypeReference<>() {}
            );
        } else {
            userResultList = userService.searchProfiles(cursor, size, keyword)
                    .stream().map(UserMapper::toDto).toList();
        }

        return CursorResponse.of(userResultList, UserResult::getNickname, size);
    }

    public UserResult updateProfile(Long id, UpdateProfileCommand dto) {
        User user = userService.updateProfile(id, UserMapper.toDomain(dto));
        return UserMapper.toDto(user);
    }

    public UserWithTokenResult oauthLogin(OAuthLoginCommand dto) {

        OAuthClient oauthClient = oauthClientFactory.create(dto.getProvider());

        OAuthUserInfo userInfo = oauthClient.getOAuthUserInfo(dto.getToken());

        User savedUser = userService.oauthJoin(userInfo);

        pointClient.createPointWallet(savedUser.getId().getValue());

        return new UserWithTokenResult(
                UserMapper.toDto(savedUser),
                jwtTokenProvider.createAuthHeaders(savedUser)
        );
    }

    public void logout(String bearerToken, Long id) {
        jwtTokenProvider.addToBlacklist(bearerToken);
        jwtTokenProvider.removeRefreshToken(id);
    }

    public void verifyPassword(Long id, String password, String bearerToken) {

        userService.verifyPassword(id, password);

        jwtTokenProvider.addToWhitelist(bearerToken);
    }

    public void updatePassword(Long id, String newPassword, String bearerToken) {
        jwtTokenProvider.checkWhitelisted(bearerToken);

        userService.updatePassword(id, newPassword);
    }

    public void delete(Long id, String bearerToken) {

        jwtTokenProvider.checkWhitelisted(bearerToken);

        userService.delete(id);

        jwtTokenProvider.addToBlacklist(bearerToken);
        jwtTokenProvider.removeRefreshToken(id);
    }

    public UserResult updateRole(Long id, String role) {
        return UserMapper.toDto(userService.updateRole(id, role));
    }

    public HttpHeaders refresh(String refreshToken) {
        Long id = jwtTokenProvider.getUserIdFromRefreshToken(refreshToken);

        User user = userService.getProfile(id);

        return jwtTokenProvider.updateAuthHeaders(user, refreshToken);
    }

    private boolean isDefaultSearchRequest(String cursor, int size, String keyword) {
        boolean isDefaultCursor = (cursor == null || cursor.isBlank());
        boolean isDefaultSize = (size == 10);
        boolean isDefaultKeyword = (keyword == null || keyword.isBlank());

        return isDefaultCursor && isDefaultKeyword && isDefaultSize;
    }
}
