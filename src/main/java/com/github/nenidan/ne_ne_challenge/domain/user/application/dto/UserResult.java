package com.github.nenidan.ne_ne_challenge.domain.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResult {

    private Long id;

    private String email;

    private String role;

    private String nickname;

    private String birth;

    private String bio;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}