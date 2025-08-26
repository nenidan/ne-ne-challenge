package com.github.nenidan.ne_ne_challenge.domain.user.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.dto.OAuthUserInfo;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.event.UserDeletedEvent;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.event.UserSavedEvent;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.exception.UserException;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.Account;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.Profile;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.User;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.vo.SocialAccount;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.repository.UserRepository;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.type.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User join(User user) {
        if(userRepository.existsByEmail(user.getAccount().getEmail())) {
            throw new UserException(UserErrorCode.DUPLICATE_EMAIL);
        }

        if(userRepository.existsByNickname(user.getProfile().getNickname())) {
            throw new UserException(UserErrorCode.DUPLICATE_NICKNAME);
        }

        if(user.getAccount().getPassword() != null) {
            user.updatePassword(passwordEncoder.encode(user.getAccount().getPassword()));
        }

        User savedUser = userRepository.save(user);
        eventPublisher.publishEvent(new UserSavedEvent(savedUser));

        return savedUser;
    }

    public User login(String email, String password) {
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(UserErrorCode.EMAIL_NOT_FOUND));

        if(!passwordEncoder.matches(password, findUser.getAccount().getPassword())) {
            throw new UserException(UserErrorCode.INVALID_PASSWORD);
        }

        return findUser;
    }

    public User getProfile(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );
    }

    public List<User> getProfiles() {
        return userRepository.findAll();
    }

    public List<User> searchProfiles(String cursor, int size, String keyword) {
        return userRepository.findByKeyword(cursor, keyword, size + 1);
    }

    public User updateProfile(Long id, Profile profile) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );

        user.updateProfile(profile);
        eventPublisher.publishEvent(new UserSavedEvent(user));

        return userRepository.update(user);
    }

    public User oauthJoin(OAuthUserInfo userInfo) {

        Optional<User> existUser = switch (userInfo.getProvider()) {
            case KAKAO -> userRepository.findByKakaoId(userInfo.getProviderId());
            case NAVER -> userRepository.findByNaverId(userInfo.getProviderId());
            case GOOGLE -> userRepository.findByGoogleId(userInfo.getProviderId());
        };

        if(existUser.isPresent()) {
            return existUser.get();
        }

        SocialAccount socialAccount = switch (userInfo.getProvider()) {
            case KAKAO -> SocialAccount.fromKakao(userInfo.getProviderId());
            case NAVER -> SocialAccount.fromNaver(userInfo.getProviderId());
            case GOOGLE -> SocialAccount.fromGoogle(userInfo.getProviderId());
        };

        User newUser = new User(
                Account.of(userInfo.getEmail(), socialAccount),
                Profile.of(userInfo.getNickname(), userInfo.getBirth(), null)
        );

        // 이메일 중복 로직 필요

        return join(newUser);
    }

    public void delete(Long id) {
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        findUser.delete();

        userRepository.delete(findUser);
        eventPublisher.publishEvent(new UserDeletedEvent(id));
    }

    public void verifyPassword(Long id, String password) {
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(password, findUser.getAccount().getPassword())) {
            throw new UserException(UserErrorCode.INVALID_PASSWORD);
        }
    }

    public void updatePassword(Long id, String newPassword) {
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        findUser.updatePassword(passwordEncoder.encode(newPassword));

        userRepository.update(findUser);
    }

    public User updateRole(Long id, String role) {

        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Role newRole = Role.of(role);

        findUser.updateRole(newRole);
        eventPublisher.publishEvent(new UserSavedEvent(findUser));

        return userRepository.update(findUser);
    }
}
