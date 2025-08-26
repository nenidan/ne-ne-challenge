package com.github.nenidan.ne_ne_challenge.domain.challenge.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.History;

public interface JpaHistoryRepository extends JpaRepository<History, Long> {

    @Query("SELECT h.userId, COUNT(h) " +
        "FROM History h " +
        "WHERE h.challengeId = :challengeId AND h.userId IN :userIds " +
        "AND h.deletedAt IS null AND h.isSuccess = true " +
        "GROUP BY h.userId")
    List<Object[]> countUserHistory(@Param("userIds") List<Long> userIds,
        @Param("challengeId") Long challengeId);
}