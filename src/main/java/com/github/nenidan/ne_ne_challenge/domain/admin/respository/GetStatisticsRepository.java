package com.github.nenidan.ne_ne_challenge.domain.admin.respository;

import com.github.nenidan.ne_ne_challenge.domain.admin.entity.StatisticData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GetStatisticsRepository extends JpaRepository<StatisticData, Long> {
}
