package com.github.nenidan.ne_ne_challenge.domain.user.presentation.mapper;

import org.mapstruct.Mapper;

import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.JoinCommand;
import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.LoginCommand;
import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.UpdateProfileCommand;
import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.UserResult;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request.JoinRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request.LoginRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request.UpdateProfileRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {

    JoinCommand toDto(JoinRequest request);

    LoginCommand toDto(LoginRequest request);

    UserResponse toResponse(UserResult result);

    UpdateProfileCommand toDto(UpdateProfileRequest request);
}
