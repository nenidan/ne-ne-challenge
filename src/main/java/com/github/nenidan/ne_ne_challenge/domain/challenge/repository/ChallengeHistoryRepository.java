package com.github.nenidan.ne_ne_challenge.domain.challenge.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.ChallengeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ChallengeHistoryRepository extends JpaRepository<ChallengeHistory, Long> {

    @Query("""
            SELECT COUNT(ch)
            FROM ChallengeHistory ch
            WHERE ch.createdAt >= :startOfDay
            AND ch.createdAt < :endOfDay
            AND ch.user.id = :userId
            AND ch.challenge.id = :challengeId
        """)
    Long countTodayHistory(@Param("userId") Long userId,
        @Param("challengeId") Long challengeId,
        @Param("startOfDay") LocalDateTime startOfDay,
        @Param("endOfDay") LocalDateTime endOfDay
    );
}
