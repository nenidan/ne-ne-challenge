package com.github.nenidan.ne_ne_challenge.domain.admin.application.service;

import com.github.nenidan.ne_ne_challenge.domain.admin.application.client.ChallengeClientPort;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.client.PaymentClientPort;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.client.PointClientPort;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.stastics.PaymentStatisticsResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.stastics.PointStatisticsResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.stastics.UserStatisticsResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository.GetStatisticsRepository;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository.StatisticsRedisRepository;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
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
    private final GetStatisticsRepository getStatisticsRepository;

    @Value("${external.base-url}")
    private String BASE_URL;

    public ChallengeStatisticsResponse getChallengeStatistics(LocalDateTime monthPeriod) {
        YearMonth ym = YearMonth.from(monthPeriod);
        String key = "statistics:challenge:" + ym;

        ChallengeStatisticsResponse cached = redisRepository.get(key, ChallengeStatisticsResponse.class);
        if(cached!=null) {return cached;}

            var dto = getStatisticsRepository.findMonthlyOne(DomainType.CHALLENGE, ym)
                    .map(ChallengeStatisticsResponse::fromModel)
                    .orElse(null);
            return dto;
    }


    public PaymentStatisticsResponse getPaymentStatistics(LocalDateTime monthPeriod) {

        YearMonth ym = YearMonth.from(monthPeriod);
        String key = "statistics:payment:" + ym;

        PaymentStatisticsResponse cached = redisRepository.get(key, PaymentStatisticsResponse.class);
        if(cached!=null) {return cached;}

        var dto = getStatisticsRepository.findMonthlyOne(DomainType.PAYMENT, ym)
                .map(PaymentStatisticsResponse::fromModel)
                .orElse(null);

        return dto;
    }

    public PointStatisticsResponse getPointStatistics(LocalDateTime monthPeriod) {

        YearMonth ym = YearMonth.from(monthPeriod);
        String key = "statistics:point:" + ym;

        PointStatisticsResponse cached = redisRepository.get(key, PointStatisticsResponse.class);
        if(cached!=null) {return cached;}

        var dto = getStatisticsRepository.findMonthlyOne(DomainType.POINT, ym)
                .map(PointStatisticsResponse::fromModel)
                .orElse(null);

        return dto;

    }

    public UserStatisticsResponse getUserStatistics(LocalDateTime monthPeriod) {

        YearMonth ym = YearMonth.from(monthPeriod);
        String key = "statistics:user:" + ym;

        UserStatisticsResponse cached = redisRepository.get(key, UserStatisticsResponse.class);
        if(cached!=null) {return cached;}

        var dto = getStatisticsRepository.findMonthlyOne(DomainType.USER, ym)
                .map(UserStatisticsResponse::fromModel)
                .orElse(null);

        return dto;

    }
}
