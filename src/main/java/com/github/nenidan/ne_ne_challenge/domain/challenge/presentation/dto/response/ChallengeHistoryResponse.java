package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChallengeHistoryResponse {

    @Schema(description = "챌린지 인증 기록 ID", example = "333")
    private Long id;

    @Schema(description = "사용자 ID", example = "22")
    private Long userId;

    @Schema(description = "챌린지 ID", example = "1")
    private Long challengeId;

    @Schema(description = "인증 내용", example = "아침 5시 59분 기상")
    private String content;

    @Schema(description = "인증 성공 여부", example = "true")
    @JsonProperty("isSuccess")
    private boolean isSuccess;

    @Schema(description = "인증 일자", example = "2025-08-20")
    private LocalDate date;

    @Schema(description = "생성 일시", example = "2025-08-20T09:14:43.039904")
    private LocalDateTime createdAt;

    @Schema(description = "수정 일시", example = "2025-08-20T09:14:43.039904")
    private LocalDateTime updatedAt;

    @Schema(description = "삭제 일시", example = "null")
    private LocalDateTime deletedAt;
}
