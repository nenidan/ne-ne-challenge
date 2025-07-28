package com.github.nenidan.ne_ne_challenge.domain.user.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.point.service.PointService;
import com.github.nenidan.ne_ne_challenge.domain.user.dto.request.JoinRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.dto.request.LoginRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.dto.request.UpdateProfileRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.dto.response.UserResponse;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserException;
import com.github.nenidan.ne_ne_challenge.domain.user.repository.UserRepository;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PointService pointService;

    @Transactional
    public UserResponse join(JoinRequest joinRequest) {

        if(userRepository.findByEmail(joinRequest.getEmail()).isPresent()) {
            throw new UserException(UserErrorCode.DUPLICATE_EMAIL);
        }

        if(userRepository.findByNickname(joinRequest.getEmail()).isPresent()) {
            throw new UserException(UserErrorCode.DUPLICATE_NICKNAME);
        }

        User newUser = joinRequest.toEntity();
        newUser.updatePassword(passwordEncoder.encode(newUser.getPassword()));

        User savedUser = userRepository.save(newUser);

        // 포인트 지갑 추가
        pointService.createWallet(savedUser.getId());

        return UserResponse.from(savedUser);
    }

    public UserResponse login(LoginRequest loginRequest) {

        User findUser = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new UserException(UserErrorCode.EMAIL_NOT_FOUND)
        );

        if(!passwordEncoder.matches(loginRequest.getPassword(), findUser.getPassword())) {
            throw new UserException(UserErrorCode.INVALID_PASSWORD);
        }

        return UserResponse.from(findUser);
    }

    public UserResponse getProfile(Long id) {
        return UserResponse.from(userRepository.findById(id).orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        ));
    }

    public CursorResponse<UserResponse, String> searchProfiles(String cursor, int size, String keyword) {

        List<UserResponse> userList = userRepository.findByKeword(cursor, keyword, size + 1)
                .stream()
                .map(UserResponse::from)
                .toList();

        boolean hasNext = userList.size() > size;

        List<UserResponse> content = hasNext ? userList.subList(0, size) : userList;

        String nextCursor = hasNext ? userList.get(userList.size() - 1).getNickname() : null;

        return new CursorResponse<>(content, nextCursor, userList.size() > size);
    }

    @Transactional
    public UserResponse updateProfile(Long id, UpdateProfileRequest updateProfileRequest) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );

        user.updateProfile(
                updateProfileRequest.getNickname(),
                updateProfileRequest.getBirth(),
                updateProfileRequest.getBio()
        );
        userRepository.flush();

        return UserResponse.from(user);
    }

}
