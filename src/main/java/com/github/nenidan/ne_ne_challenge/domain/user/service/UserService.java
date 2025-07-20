package com.github.nenidan.ne_ne_challenge.domain.user.service;

import com.github.nenidan.ne_ne_challenge.domain.user.dto.request.JoinRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.dto.response.UserResponse;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserException;
import com.github.nenidan.ne_ne_challenge.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserResponse join(JoinRequest joinRequest) {

        existsByEmail(joinRequest.getEmail());

        User user = joinRequest.toEntity();
        user.updatePassword(passwordEncoder.encode(user.getPassword()));

        return UserResponse.from(userRepository.save(user));
    }




    public void existsByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserException(UserErrorCode.DUPLICATE_EMAIL);
        }
    }
}
