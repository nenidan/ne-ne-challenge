package com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UpdateProfileRequest {

    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(min = 2, max = 12, message = "닉네임은 2자 이상 12자 이하여야 합니다.")
    private String nickname;

    @NotNull(message = "생년월일은 필수입니다.")
    private LocalDate birth;

    private String bio;
}
