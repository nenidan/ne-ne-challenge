package com.github.nenidan.ne_ne_challenge.domain.user.application.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateProfileCommand {

    private String nickname;

    private LocalDate birth;

    private String bio;
}
