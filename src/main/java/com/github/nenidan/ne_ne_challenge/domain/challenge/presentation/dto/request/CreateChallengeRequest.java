package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateChallengeRequest {

    @Schema(description = "챌린지 이름", example = "아침 6시 기상")
    private String name;

    @Schema(description = "챌린지 설명", example = "매일 아침 6시에 기상하는 챌린지")
    private String description;

    @Schema(description = "최소 참여 인원", example = "5")
    private int minParticipants;

    @Schema(description = "최대 참여 인원", example = "50")
    private int maxParticipants;

    @Schema(description = "챌린지 시작일", example = "2025-09-01")
    private LocalDate startAt;

    @Schema(description = "챌린지 종료일", example = "2025-09-30")
    private LocalDate dueAt;

    @Schema(description = "챌린지 카테고리", example = "HABIT")
    private ChallengeCategory category;

    @Schema(description = "참가비", example = "1000")
    private int participationFee;
}
