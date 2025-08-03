package com.github.nenidan.ne_ne_challenge.domain.user.presentation.mapper;

import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.*;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request.JoinRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request.LoginRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request.OAuthLoginRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request.UpdateProfileRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    JoinCommand toDto(JoinRequest request);

    LoginCommand toDto(LoginRequest request);

    UserResponse toResponse(UserResult result);

    UpdateProfileCommand toDto(UpdateProfileRequest request);

    OAuthLoginCommand toDto(OAuthLoginRequest request);
}
