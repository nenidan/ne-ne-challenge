package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request;

import java.time.LocalDate;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentSearchRequest {

    private Long cursor;

    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
    @Max(value = 20, message = "페이지 크기는 20 이하여야 합니다.")
    private int size = 10;

    private String status;

    private LocalDate startDate;

    private LocalDate endDate;
}
