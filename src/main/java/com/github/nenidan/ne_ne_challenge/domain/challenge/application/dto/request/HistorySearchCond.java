package com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// Todo 빈 검증 제약 조건
@Getter
@Setter
public class HistorySearchCond {

    @NotNull
    private Long userId;

    private LocalDateTime cursor = LocalDateTime.of(9999, 12, 31, 23, 59, 59);

    private int size = 10;
}
