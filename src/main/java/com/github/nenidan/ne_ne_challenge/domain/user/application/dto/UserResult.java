package com.github.nenidan.ne_ne_challenge.domain.user.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResult {

    private Long id;

    private String email;

    private String role;

    private String nickname;

    private LocalDate birth;

    private String bio;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}