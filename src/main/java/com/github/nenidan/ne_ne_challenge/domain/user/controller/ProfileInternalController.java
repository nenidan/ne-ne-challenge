package com.github.nenidan.ne_ne_challenge.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.user.dto.request.UpdateProfileRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.dto.response.UserResponse;
import com.github.nenidan.ne_ne_challenge.domain.user.service.UserService;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class ProfileInternalController {

    private final UserService userService;

    @GetMapping("/profiles/{id}")
    public ResponseEntity<UserResponse> getMyProfile(@PathVariable Long id) {

        UserResponse profile = userService.getProfile(id);

        return ResponseEntity.ok().body(profile);
    }

}
