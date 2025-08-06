package com.github.nenidan.ne_ne_challenge.domain.challenge.infrastructure.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaHistoryRepository extends JpaRepository<History, Long> {

//    List<History> findByChallenge_Id(Long challengeId);
//
//    @Query("""
//            SELECT COUNT(h)
//            FROM History h
//            WHERE h.createdAt >= :startOfDay
//            AND h.createdAt < :endOfDay
//            AND h.userId = :userId
//            AND h.challenge.id = :challengeId
//        """)
//    Long countTodayHistory(@Param("userId") Long userId,
//        @Param("challengeId") Long challengeId,
//        @Param("startOfDay") LocalDateTime startOfDay,
//        @Param("endOfDay") LocalDateTime endOfDay
//    );
//
//    @Query(value = """
//        SELECT h.*
//        FROM history h
//        WHERE h.user_id = :userId
//        AND h.challenge_id = :challengeId
//        AND (:cursor IS NULL OR h.created_at <= :cursor)
//        ORDER BY h.created_at DESC
//        LIMIT :limit
//        """, nativeQuery = true)
//    List<History> getChallengeHistoryList(
//        @Param("challengeId") Long challengeId,
//        @Param("userId") Long userId,
//        @Param("cursor") LocalDateTime cursor,
//        @Param("limit") int limit
//    );
//
//    @Query("""
//            SELECT h
//            FROM History h
//            WHERE h.createdAt >= :startOfDay AND h.createdAt < :endOfDay
//
//        """)
//    void checkDuplicate(@Param("userId") Long userId,
//        @Param("challengeId") Long challengeId,
//        @Param("startOfDay") LocalDateTime startOfToday,
//        @Param("endOfDay") LocalDateTime endOfToday
//    );
}