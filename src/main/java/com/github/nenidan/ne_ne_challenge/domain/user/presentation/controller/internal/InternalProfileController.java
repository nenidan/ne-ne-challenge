package com.github.nenidan.ne_ne_challenge.domain.user.presentation.controller.internal;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.UserResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.nenidan.ne_ne_challenge.domain.user.application.UserFacade;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.response.UserResponse;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class InternalProfileController {

    private final UserFacade userFacade;
    private final UserMapper userMapper;

    @GetMapping("/profiles/{id}")
    public ResponseEntity<UserResponse> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                userMapper.toResponse(userFacade.getProfile(id))
        );
    }

    @GetMapping("/profiles")
    public List<UserResult> getProfiles() {
        return userFacade.getProfileAll();
    }
}
