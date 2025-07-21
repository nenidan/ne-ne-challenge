package com.github.nenidan.ne_ne_challenge.domain.challenge.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    @Query(value = """
        SELECT c.*
        FROM challenge c
        LEFT JOIN challenge_user cu ON cu.challenge_id = c.id AND (:userId IS NULL OR cu.user_id = :userId)
        WHERE(:name IS NULL OR c.name LIKE CONCAT('%', :name, '%'))
                AND (:cursor IS NULL OR c.created_at <= :cursor)
                AND (:status IS NULL OR c.status = :status)
                AND (:dueAt IS NULL OR c.due_at < :dueAt)
                AND (:category IS NULL OR c.category = :category)
                AND (:maxParticipationFee IS NULL OR c.participation_fee <= :maxParticipationFee)
        ORDER BY c.created_at DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<Challenge> getChallengeList(@Param("userId") Long userId,
        @Param("name") String name,
        @Param("status") ChallengeStatus status,
        @Param("dueAt") LocalDate dueAt,
        @Param("category") ChallengeCategory category,
        @Param("maxParticipationFee") Integer maxParticipationFee,
        @Param("cursor") LocalDateTime cursor,
        @Param("limit") int limit
    );
}