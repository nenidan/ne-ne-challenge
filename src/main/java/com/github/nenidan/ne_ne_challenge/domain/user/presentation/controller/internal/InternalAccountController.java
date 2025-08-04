package com.github.nenidan.ne_ne_challenge.domain.user.presentation.controller.internal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.user.application.UserFacade;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request.UpdateRoleRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.response.UserResponse;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.mapper.UserMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class InternalAccountController {

    private final UserFacade userFacade;
    private final UserMapper userMapper;

    @PatchMapping("/accounts/{id}/role")
    public ResponseEntity<ApiResponse<UserResponse>> updateRole(
            @PathVariable Long id,
            @RequestBody UpdateRoleRequest request
    ) {

        return ApiResponse.success(
                HttpStatus.OK,
                "권한이 재설정 되었습니다.",
                userMapper.toResponse(userFacade.updateRole(id, request.getRole()))
        );
    }
}
