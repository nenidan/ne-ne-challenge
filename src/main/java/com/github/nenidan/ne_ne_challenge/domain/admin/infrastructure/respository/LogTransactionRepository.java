package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.request.LogSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.entity.AopLog;

@Repository
public interface LogTransactionRepository extends JpaRepository<AopLog, Long> {

    @Query("SELECT a " +
            "FROM AopLog a WHERE a.type = :type " +
            "AND (:#{#cond.userId} IS NULL OR a.userId = :#{#cond.userId}) " +
            "AND (:#{#cond.cursor} IS NULL OR a.createdAt < :#{#cond.cursor}) " +
            "ORDER BY a.createdAt DESC")
    List<AopLog> findLogs(@Param("type") DomainType type, @Param("cond") LogSearchCond cond);

}
