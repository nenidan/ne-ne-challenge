package com.github.nenidan.ne_ne_challenge.domain.admin.service;

import com.github.nenidan.ne_ne_challenge.domain.admin.dto.request.DashboardSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.admin.dto.response.StatisticsResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.dto.response.stastics.ChallengeStatisticsResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.respository.GetStatisticsRepository;
import com.github.nenidan.ne_ne_challenge.domain.admin.type.DomainType;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.inner.InnerChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.inner.InnerChallengeUserResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.service.ChallengeService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.service.ChallengeUserService;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
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

    private final ChallengeUserService challengeUserService;
    private final ChallengeService challengeService;

    //최초 계산 및 캐싱용 ( redis )
    private final GetStatisticsRepository getStatisticsRepository;

    public ChallengeStatisticsResponse getChallengeStatistics(LocalDateTime monthPeriod) {

        List<InnerChallengeResponse> challengeList = challengeService.getAllChallengeList();
        List<InnerChallengeUserResponse> challengeUserList = challengeUserService.getAllChallengeUserList();

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
