package com.github.nenidan.ne_ne_challenge.domain.admin.respository;

import com.github.nenidan.ne_ne_challenge.domain.admin.dto.request.DashboardSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.admin.dto.response.AopLogResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.entity.AopLog;
import com.github.nenidan.ne_ne_challenge.domain.admin.type.DomainType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AopLogRepository extends JpaRepository<AopLog, Long> {

    @Query("SELECT a " +
            "FROM AopLog a WHERE a.type = :type " +
            "AND (:#{#cond.userId} IS NULL OR a.userId = :#{#cond.userId}) " +
            "AND (:#{#cond.cursor} IS NULL OR a.createdAt < :#{#cond.cursor}) " +
            "ORDER BY a.createdAt DESC")
    List<AopLog> findLogs(@Param("type") DomainType type, @Param("cond") DashboardSearchCond cond);
}
