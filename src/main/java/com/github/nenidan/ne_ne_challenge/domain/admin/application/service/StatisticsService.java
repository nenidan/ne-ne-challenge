package com.github.nenidan.ne_ne_challenge.domain.admin.application.service;

import com.github.nenidan.ne_ne_challenge.domain.admin.application.client.ChallengeClientPort;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.client.PaymentClientPort;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.client.PointClientPort;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.stastics.PaymentStatisticsResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.stastics.PointStatisticsResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.stastics.UserStatisticsResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository.StatisticsRedisRepository;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.ChallengeDto;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.ChallengeUserDto;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.stastics.ChallengeStatisticsResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.PaymentDto;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.PointDto;
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

    private final StatisticsRedisRepository redisRepository;

    public ChallengeStatisticsResponse getChallengeStatistics(LocalDateTime monthPeriod) {

        String key = "statistics:challenge:" + YearMonth.from(monthPeriod);
        return redisRepository.get(key, ChallengeStatisticsResponse.class);

    }


    public PaymentStatisticsResponse getPaymentStatistics(LocalDateTime monthPeriod) {

        String key = "statistics:payment:" + YearMonth.from(monthPeriod);
        return redisRepository.get(key, PaymentStatisticsResponse.class);

    }

    public PointStatisticsResponse getPointStatistics(LocalDateTime monthPeriod) {

        String key = "statistics:point:" + YearMonth.from(monthPeriod);
        return redisRepository.get(key, PointStatisticsResponse.class);

    }

    public UserStatisticsResponse getUserStatistics(LocalDateTime monthPeriod) {

        String key = "statistics:user:" + YearMonth.from(monthPeriod);
        return redisRepository.get(key, UserStatisticsResponse.class);

    }
}
