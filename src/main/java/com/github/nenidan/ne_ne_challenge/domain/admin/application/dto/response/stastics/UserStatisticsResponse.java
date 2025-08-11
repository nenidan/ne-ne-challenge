package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.stastics;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class UserStatisticsResponse extends StatisticsResponse{
    private Long id;

    private String email;

    private String role;

    private String nickname;

    private LocalDate birth;

    private String bio;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public UserStatisticsResponse(String type, LocalDateTime createdAt) {
        super(type, createdAt);
    }
}
