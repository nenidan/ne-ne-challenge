package com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {

    @Schema(description = "닉네임", example = "testtest")
    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(min = 2, max = 12, message = "닉네임은 2자 이상 12자 이하여야 합니다.")
    private String nickname;

    @Schema(description = "생년월일", example = "2001-01-01")
    @NotNull(message = "생년월일은 필수입니다.")
    private LocalDate birth;

    @Schema(description = "자기소개", example = "반갑습니다!")
    private String bio;
}
