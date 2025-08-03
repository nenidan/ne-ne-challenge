package com.github.nenidan.ne_ne_challenge.domain.admin.application.service;

import com.github.nenidan.ne_ne_challenge.domain.admin.application.client.ChallengeClientPort;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.ChallengeDto;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.ChallengeUserDto;
import com.github.nenidan.ne_ne_challenge.domain.admin.presentation.dto.response.stastics.ChallengeStatisticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticsService {

    private final ChallengeClientPort challengeClientPort;

    //최초 계산 및 캐싱용 ( redis )
    //private final GetStatisticsRepository getStatisticsRepository;

    public ChallengeStatisticsResponse getChallengeStatistics(LocalDateTime monthPeriod) {

        List<ChallengeDto> challengeList = challengeClientPort.getAllChallenges();
        List<ChallengeUserDto> challengeUserList = challengeClientPort.getAllChallengeUsers();

        // 일별 참가자 수 계산
        Map<LocalDate, Long> dailyParticipants = challengeUserList.stream()
                .collect(Collectors.groupingBy(
                        cu -> cu.getCreatedAt().toLocalDate(), // 참여한 날짜 기준
                        Collectors.counting()
                ));

        // 월별 참가자 수 계산
        Map<YearMonth, Long> monthlyParticipants = challengeUserList.stream()
                .collect(Collectors.groupingBy(
                        cu -> YearMonth.from(cu.getCreatedAt()),
                        Collectors.counting()
                ));

        // 챌린지 참여율 (시작된 챌린지 수 / 전체 챌린지 수)
        long startedCount = challengeList.stream()
                .filter(c -> c.getStartedAt() != null)
                .count();

        double participationRate = challengeList.isEmpty() ? 0.0 :
                (double) startedCount / challengeList.size();

        return new ChallengeStatisticsResponse("challenge",
                monthPeriod,
                dailyParticipants,
                monthlyParticipants,
                participationRate
        );

    }



}
