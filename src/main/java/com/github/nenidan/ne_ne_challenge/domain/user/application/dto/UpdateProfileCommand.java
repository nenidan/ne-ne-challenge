package com.github.nenidan.ne_ne_challenge.domain.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UpdateProfileCommand {

    private String nickname;

    private LocalDate birth;

    private String bio;
}
