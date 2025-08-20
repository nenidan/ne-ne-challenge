package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCancelRequest {

    @Schema(description = "취소 사유", example = "단순 변심")
    @NotBlank(message = "취소 사유는 필수입니다.")
    @Size(min = 2, max = 200, message = "취소 사유는 2자 이상 200자 이하여야 합니다.")
    private String cancelReason;
}
