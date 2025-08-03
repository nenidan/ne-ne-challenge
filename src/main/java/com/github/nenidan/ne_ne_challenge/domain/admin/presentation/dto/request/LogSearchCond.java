package com.github.nenidan.ne_ne_challenge.domain.admin.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.logging.LogLevel;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class LogSearchCond {

    private Long Id;
    private String keyword;
    private LogLevel level;

    private LocalDateTime cursor = LocalDateTime.of(9999, 12, 31, 23, 59, 59);

    private int size = 10;

}
