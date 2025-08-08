package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.request;

import java.time.LocalDateTime;

import org.springframework.boot.logging.LogLevel;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogSearchCond {

    private Long userId;
    private String keyword;
    private LogLevel level;

    private LocalDateTime cursor = LocalDateTime.of(9999, 12, 31, 23, 59, 59);

    private int size = 10;

}
