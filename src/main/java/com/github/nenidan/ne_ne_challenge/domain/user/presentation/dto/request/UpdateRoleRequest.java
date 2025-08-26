package com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleRequest {

    @Schema(description = "권한", example = "test@gmail.com")
    @NotBlank(message = "권한은 필수입니다.")
    private String role;
}
