package com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PointSearchCommand {

    private Long cursor;

    private int size;

    private String reason;

    private LocalDate startDate;

    private LocalDate endDate;
}
