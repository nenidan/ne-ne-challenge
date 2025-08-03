package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCancelRequest {

    @NotBlank(message = "취소 사유는 필수입니다.")
    @Size(min = 2, max = 200, message = "취소 사유는 2자 이상 200자 이하여야 합니다.")
    private String cancelReason;
}
