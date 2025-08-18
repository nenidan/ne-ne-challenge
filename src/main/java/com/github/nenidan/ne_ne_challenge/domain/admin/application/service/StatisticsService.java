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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

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
    private final RestClient restClient;

    @Value("${external.base-url}")
    private String BASE_URL;

    public ChallengeStatisticsResponse getChallengeStatistics(LocalDateTime monthPeriod) {

        String key = "statistics:challenge:" + YearMonth.from(monthPeriod);
        ChallengeStatisticsResponse cached = redisRepository.get(key, ChallengeStatisticsResponse.class);
        if(cached==null) {
            cached = restClient.post()
                    .uri(BASE_URL+"/batch/statistics/challenge?monthPeriod=" + monthPeriod)
                    .retrieve()
                    .body(ChallengeStatisticsResponse.class);
        }
        return cached;
    }


    public PaymentStatisticsResponse getPaymentStatistics(LocalDateTime monthPeriod) {

        String key = "statistics:payment:" + YearMonth.from(monthPeriod);
        PaymentStatisticsResponse cached = redisRepository.get(key, PaymentStatisticsResponse.class);

        if(cached==null) {
            cached = restClient.post()
                    .uri(BASE_URL+"/batch/statistics/payment?monthPeriod=" + monthPeriod)
                    .retrieve()
                    .body(PaymentStatisticsResponse.class);
        }
        return cached;
    }

    public PointStatisticsResponse getPointStatistics(LocalDateTime monthPeriod) {

        String key = "statistics:point:" + YearMonth.from(monthPeriod);
        PointStatisticsResponse cached = redisRepository.get(key, PointStatisticsResponse.class);

        if(cached==null) {
            cached = restClient.post()
                    .uri(BASE_URL+"/batch/statistics/point?monthPeriod=" + monthPeriod)
                    .retrieve()
                    .body(PointStatisticsResponse.class);
        }
        return cached;

    }

    public UserStatisticsResponse getUserStatistics(LocalDateTime monthPeriod) {

        String key = "statistics:user:" + YearMonth.from(monthPeriod);
        UserStatisticsResponse cached = redisRepository.get(key, UserStatisticsResponse.class);

        if(cached==null) {
            cached = restClient.post()
                    .uri(BASE_URL+"/batch/statistics/user?monthPeriod=" + monthPeriod)
                    .retrieve()
                    .body(UserStatisticsResponse.class);
        }

        return cached;
    }
}
