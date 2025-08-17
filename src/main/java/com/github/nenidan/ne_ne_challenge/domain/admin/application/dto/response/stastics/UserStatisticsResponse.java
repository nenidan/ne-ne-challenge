package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.stastics;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class UserStatisticsResponse extends StatisticsResponse{
    private Long newUsersCount;
    private Long totalUsersCount;

    public UserStatisticsResponse(String type, LocalDateTime createdAt, Long newUsersCount, Long totalUsersCount) {
        super(type, createdAt);
        this.newUsersCount = newUsersCount;
        this.totalUsersCount = totalUsersCount;
    }
}
