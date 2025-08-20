package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.response;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeResponse {

    @Schema(description = "챌린지 ID", example = "1")
    private Long id;

    @Schema(description = "챌린지 이름", example = "아침 6시 기상")
    private String name;

    @Schema(description = "챌린지 설명", example = "매일 아침 6시에 기상하는 챌린지")
    private String description;

    @Schema(description = "챌린지 상태", example = "ONGOING")
    private ChallengeStatus status;

    @Schema(description = "챌린지 카테고리", example = "HABIT")
    private ChallengeCategory category;

    @Schema(description = "방장의 사용자 ID", example = "22")
    private Long hostId;

    @Schema(description = "최소 참여 인원", example = "2")
    private int minParticipants;

    @Schema(description = "최대 참여 인원", example = "5")
    private int maxParticipants;

    @Schema(description = "현재 참여 인원", example = "4")
    private int currentParticipantCount;

    @Schema(description = "참가비", example = "1000")
    private int participationFee;

    @Schema(description = "총 참가비", example = "4000")
    private int totalFee;

    @Schema(description = "챌린지 종료일", example = "2025-09-30")
    private LocalDate dueAt;

    @Schema(description = "챌린지 시작일", example = "2025-09-01")
    private LocalDate startAt;

    @Schema(description = "생성 일시", example = "2025-08-04T09:14:43.039904")
    private LocalDateTime createdAt;

    @Schema(description = "수정 일시", example = "2025-08-04T09:14:43.039904")
    private LocalDateTime updatedAt;

    @Schema(description = "삭제 일시", example = "null")
    private LocalDateTime deletedAt;
}
