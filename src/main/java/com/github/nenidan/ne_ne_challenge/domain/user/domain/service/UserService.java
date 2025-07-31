package com.github.nenidan.ne_ne_challenge.domain.user.domain.service;

import com.github.nenidan.ne_ne_challenge.domain.user.domain.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.exception.UserException;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.Profile;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.User;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.repository.UserRepository;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User join(User user) {
        if(userRepository.existsByEmail(user.getAccount().getEmail())) {
            throw new UserException(UserErrorCode.DUPLICATE_EMAIL);
        }

        if(userRepository.existsByNickname(user.getProfile().getNickname())) {
            throw new UserException(UserErrorCode.DUPLICATE_NICKNAME);
        }

        user.updatePassword(passwordEncoder.encode(user.getAccount().getPassword()));

        return userRepository.save(user);
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

    public CursorResponse<User, String> searchProfiles(String cursor, int size, String keyword) {
        List<User> userList = userRepository.findByKeyword(cursor, keyword, size);

        boolean hasNext = userList.size() > size;

        List<User> content = hasNext ? userList.subList(0, size) : userList;

        String nextCursor = hasNext ? userList.get(userList.size() - 1).getProfile().getNickname() : null;

        return new CursorResponse<>(content, nextCursor, userList.size() > size);
    }

    public User updateProfile(Long id, Profile profile) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );

        user.updateProfile(profile);

        return userRepository.updateProfile(user);
    }
}
