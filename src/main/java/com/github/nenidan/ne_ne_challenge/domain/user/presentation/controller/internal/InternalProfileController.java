package com.github.nenidan.ne_ne_challenge.domain.user.presentation.controller.internal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.user.application.UserFacade;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.response.UserResponse;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

import java.util.List;

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
    public ResponseEntity<List<UserResponse>> getProfiles() {
        return ResponseEntity.ok().body(
                userFacade.getProfiles().stream().map(userMapper::toResponse).toList()
        );
    }
}
