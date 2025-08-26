package com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PointSearchRequest {

    @Schema(description = "커서 (페이징)", example = "1")
    private Long cursor;

    @Schema(description = "조회할 개수", example = "10", defaultValue = "10", minimum = "1", maximum = "20")
    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
    @Max(value = 20, message = "페이지 크기는 20 이하여야 합니다.")
    private int size = 10;

    @Schema(description = "포인트 사용 사유", example = "CHARGE")
    private String reason;

    @Schema(description = "검색 시작 날짜", example = "2025-04-07")
    private LocalDate startDate;

    @Schema(description = "검색 종료 날짜", example = "2025-08-29")
    private LocalDate endDate;
}
