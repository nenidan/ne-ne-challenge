package com.github.nenidan.ne_ne_challenge.domain.challenge.infrastructure.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface JpaChallengeRepository extends JpaRepository<Challenge, Long> {

    @Query(value = """
        SELECT c.*
        FROM challenge c
        LEFT JOIN participant p ON p.challenge_id = c.id AND (:userId IS NULL OR p.user_id = :userId)
        WHERE(:name IS NULL OR c.name LIKE CONCAT('%', :name, '%'))
                AND (c.deleted_at IS NULL)
                AND (:cursor IS NULL OR c.created_at <= :cursor)
                AND (:status IS NULL OR c.status = :status)
                AND (:dueAt IS NULL OR c.due_at < :dueAt)
                AND (:category IS NULL OR c.category = :category)
                AND (:maxParticipationFee IS NULL OR c.participation_fee <= :maxParticipationFee)
        ORDER BY c.created_at DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<Challenge> getChallengeList(
        @Param("userId") Long userId,
        @Param("name") String name,
        @Param("status") ChallengeStatus status,
        @Param("dueAt") LocalDate dueAt,
        @Param("category") ChallengeCategory category,
        @Param("maxParticipationFee") Integer maxParticipationFee,
        @Param("cursor") LocalDateTime cursor,
        @Param("limit") int limit
    );
}
