package com.github.nenidan.ne_ne_challenge.domain.user.application;

import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.OAuthClient;
import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.OAuthClientFactory;
import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.dto.OAuthUserInfo;
import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.*;
import com.github.nenidan.ne_ne_challenge.domain.user.application.mapper.UserMapper;
import com.github.nenidan.ne_ne_challenge.domain.user.application.service.JwtTokenProvider;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.User;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.service.UserService;

import com.github.nenidan.ne_ne_challenge.global.client.point.PointClient;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PointClient pointClient;
    private final OAuthClientFactory oauthClientFactory;

    @Transactional
    public UserWithTokenResult join(JoinCommand joinCommand) {

        User user = UserMapper.toDomain(joinCommand);

        User savedUser = userService.join(user);

        pointClient.createPointWallet(savedUser.getId().getValue());

        return new UserWithTokenResult(
                UserMapper.toDto(savedUser),
                jwtTokenProvider.createAuthHeaders(savedUser)
        );
    }

    @Transactional
    public UserWithTokenResult login(LoginCommand loginCommand) {

        User user = userService.login(loginCommand.getEmail(), loginCommand.getPassword());

        return new UserWithTokenResult(
                UserMapper.toDto(user),
                jwtTokenProvider.createAuthHeaders(user)
        );
    }

    @Transactional
    public UserResult getProfile(Long id) {
        return UserMapper.toDto(userService.getProfile(id));
    }

    @Transactional
    public CursorResponse<UserResult, String> searchProfiles(String cursor, int size, String keyword) {
        CursorResponse<User, String> userList = userService.searchProfiles(cursor, size, keyword);
        List<UserResult> content = userList.getContent().stream()
                .map(UserMapper::toDto)
                .toList();
        return new CursorResponse<>(content, userList.getNextCursor(), userList.isHasNext());
    }

    @Transactional
    public UserResult updateProfile(Long id, UpdateProfileCommand dto) {
        User user = userService.updateProfile(id, UserMapper.toDomain(dto));
        return UserMapper.toDto(user);
    }

    @Transactional
    public UserWithTokenResult oauthLogin(OAuthLoginCommand dto) {

        OAuthClient oauthClient = oauthClientFactory.create(dto.getProvider());

        OAuthUserInfo userInfo = oauthClient.getOAuthUserInfo(dto.getToken());

        User savedUser = userService.oauthJoin(userInfo);

        // Todo: 포인트 지갑 추가

        return new UserWithTokenResult(
                UserMapper.toDto(savedUser),
                jwtTokenProvider.createAuthHeaders(savedUser)
        );
    }

    @Transactional
    public void logout(String bearerToken) {
        jwtTokenProvider.addToBlacklist(bearerToken);
    }

    @Transactional
    public void verifyPassword(Long id, String password, String bearerToken) {

        userService.verifyPassword(id, password);

        jwtTokenProvider.addToWhitelist(bearerToken);
    }

    @Transactional
    public void updatePassword(Long id, String newPassword, String bearerToken) {
        jwtTokenProvider.checkWhitelisted(bearerToken);

        userService.updatePassword(id, newPassword);
    }

    @Transactional
    public void delete(Long id, String bearerToken) {

        jwtTokenProvider.checkWhitelisted(bearerToken);

        userService.delete(id);

        jwtTokenProvider.addToBlacklist(bearerToken);
    }

    public UserResult updateRole(Long id, String role) {
        return UserMapper.toDto(userService.updateRole(id, role));
    }
}
