package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class HistorySearchRequest {

    @Schema(description = "조회할 사용자 ID", example = "22", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long userId;

    @Schema(
        description = "커서 기반 페이지네이션 키 (기본값: 9999-12-31T23:59:59, createdAt 내림차순 기준 첫 페이지)",
        example = "2025-08-20T12:00:00"
    )
    private LocalDateTime cursor = LocalDateTime.of(9999, 12, 31, 23, 59, 59);

    @Schema(description = "한 번에 조회할 데이터 개수 (기본값: 10)", example = "10")
    private int size = 10;
}
