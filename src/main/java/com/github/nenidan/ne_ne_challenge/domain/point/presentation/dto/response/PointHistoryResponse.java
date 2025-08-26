package com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointHistoryResponse {

    @Schema(description = "포인트 거래 ID", example = "1")
    private Long pointTransactionId;

    @Schema(description = "포인트 금액 (음수: 차감, 양수: 충전)", example = "10000")
    private int amount;

    @Schema(description = "포인트 사용 사유", example = "CHARGE")
    private String reason;

    @Schema(description = "사유 설명", example = "포인트 결제")
    private String description;
}
