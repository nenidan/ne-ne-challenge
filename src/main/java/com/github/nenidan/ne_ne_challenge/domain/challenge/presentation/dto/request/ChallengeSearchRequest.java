package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChallengeSearchRequest {

    @Schema(description = "챌린지 이름 검색어", example = "기상")
    private String name;

    @Schema(description = "챌린지 상태", example = "ONGOING")
    private ChallengeStatus status;

    @Schema(description = "챌린지 시작일 검색 조건", example = "2025-09-01")
    private LocalDate startAt;

    @Schema(description = "챌린지 종료일 검색 조건", example = "2025-09-30")
    private LocalDate dueAt;

    @Schema(description = "챌린지 카테고리", example = "HABIT")
    private ChallengeCategory category;

    @Schema(description = "최대 참가비 조건", example = "5000")
    private Integer maxParticipationFee;

    @Schema(
        description = "커서 기반 페이지네이션 키 (기본값: 9999-12-31T23:59:59, createdAt 내림차순 기준 첫 페이지)",
        example = "2025-08-20T12:00:00"
    )
    private LocalDateTime cursor = LocalDateTime.of(9999, 12, 31, 23, 59, 59);

    @Schema(description = "조회할 데이터 개수 (기본값: 10)", example = "20")
    private int size = 10;
}
