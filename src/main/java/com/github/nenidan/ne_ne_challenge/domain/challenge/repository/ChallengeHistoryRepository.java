package com.github.nenidan.ne_ne_challenge.domain.challenge.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.ChallengeHistory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Query(value = """
        SELECT ch.*
        FROM challenge_history ch
        WHERE ch.user_id = :userId
        AND ch.challenge_id = :challengeId
        AND (:cursor IS NULL OR ch.created_at <= :cursor)
        ORDER BY ch.created_at DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<ChallengeHistory> getChallengeHistoryList(@Param("userId") Long userId,
        @Param("challengeId") Long challengeId,
        @Param("cursor") LocalDateTime cursor,
        @Param("limit") int limit
    );

    int countByChallengeAndUserAndIsSuccessTrue(Challenge challenge, User user);
}
