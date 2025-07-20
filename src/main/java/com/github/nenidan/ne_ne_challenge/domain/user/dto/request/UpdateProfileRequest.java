package com.github.nenidan.ne_ne_challenge.domain.user.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateProfileRequest {

    private String nickname;

    private LocalDate birth;

    private String bio;
}
