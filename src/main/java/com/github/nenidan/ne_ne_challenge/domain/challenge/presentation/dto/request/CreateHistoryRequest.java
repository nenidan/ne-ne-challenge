package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreateHistoryRequest {

    @Schema(description = "인증 내용", example = "새벽 5시 59분 기상")
    private String content;
}
