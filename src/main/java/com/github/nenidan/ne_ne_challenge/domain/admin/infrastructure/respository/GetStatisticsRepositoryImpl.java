package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.respository;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.StatisticDataModel;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository.GetStatisticsRepository;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.mapper.StatisticMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GetStatisticsRepositoryImpl implements GetStatisticsRepository{

    private final StatisticTransactionRespository statisticTransactionRespository;

    @Override
    public Optional<StatisticDataModel> findMonthlyOne(DomainType domainType, YearMonth ym) {
        LocalDate anchor = ym.atDay(1);
        return statisticTransactionRespository.findByTypeAndStatDate(domainType, anchor)
                .map(StatisticMapper::toDomain);
    }

}
