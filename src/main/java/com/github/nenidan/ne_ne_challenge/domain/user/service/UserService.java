package com.github.nenidan.ne_ne_challenge.domain.user.service;

import com.github.nenidan.ne_ne_challenge.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

}
