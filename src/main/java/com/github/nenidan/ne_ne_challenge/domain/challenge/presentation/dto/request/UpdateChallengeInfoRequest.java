package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateChallengeInfoRequest {

    @Schema(description = "새로운 챌린지 이름", example = "하루에 시 하나 필사")
    private String name;

    @Schema(description = "새로운 챌린지 설명", example = "매일 시 하나를 정해 핸드폰으로 따라 적습니다.")
    private String description;

    @Schema(description = "새로운 챌린지 시작일", example = "2025-09-01")
    private LocalDate startAt;

    @Schema(description = "새로운 챌린지 종료일", example = "2025-09-30")
    private LocalDate dueAt;

    @Schema(description = "새로운 챌린지 카테고리", example = "STUDY")
    private ChallengeCategory category;
}