package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.respository;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.entity.StatisticData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticTransactionRespository  extends JpaRepository<StatisticData, Long>{

    @Query("select s from StatisticData s " +
            "where s.type = :type and s.statDate = :statDate")
    Optional<StatisticData> findByTypeAndStatDate(DomainType type, LocalDate statDate);
}
