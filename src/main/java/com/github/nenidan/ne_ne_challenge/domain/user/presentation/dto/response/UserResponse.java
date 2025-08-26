package com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    @Schema(description = "유저 ID", example = "1")
    private Long id;

    @Schema(description = "이메일", example = "test@gmail.com")
    private String email;

    @Schema(description = "비밀번호", example = "test1234!")
    private String role;

    @Schema(description = "닉네임", example = "test")
    private String nickname;

    @Schema(description = "생년월일", example = "2000-01-01")
    private LocalDate birth;

    @Schema(description = "자기소개", example = "안녕하세요~!")
    private String bio;

    @Schema(description = "생성일")
    private LocalDateTime createdAt;

    @Schema(description = "수정일")
    private LocalDateTime updatedAt;

    @Schema(description = "삭제일", example = "null")
    private LocalDateTime deletedAt;
}
