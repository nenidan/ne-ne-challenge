package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.stastics;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Map;

@Getter
public class ChallengeStatisticsResponse extends StatisticsResponse{

    private Map<LocalDate, Long> dailyParticipants;   // 일별 참가자 수
    private Map<YearMonth, Long> monthlyParticipants; // 월별 참가자 수
    private double participationRate;                 // 챌린지 참여율 (시작된 챌린지 / 전체 챌린지)

    public ChallengeStatisticsResponse(String type, LocalDateTime createdAt, Map<LocalDate, Long> dailyParticipants,
                                       Map<YearMonth, Long> monthlyParticipants,
                                       double participationRate) {
        super(type, createdAt);
        this.dailyParticipants = dailyParticipants; 
        this.monthlyParticipants = monthlyParticipants;
        this.participationRate = participationRate;
    }

}
