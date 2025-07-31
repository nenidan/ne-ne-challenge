package com.github.nenidan.ne_ne_challenge.domain.admin.dto.request;

import lombok.Getter;
import org.springframework.boot.logging.LogLevel;

import java.time.LocalDateTime;

@Getter
public class LogSearchCond {

    private Long Id;
    private String keyword;
    private LogLevel level;

    private LocalDateTime cursor = LocalDateTime.of(9999, 12, 31, 23, 59, 59);

    private int size = 10;

}
