package com.github.nenidan.ne_ne_challenge.domain.challenge.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.ChallengeUser;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeUserRepository extends JpaRepository<ChallengeUser, Long> {

    List<ChallengeUser> findByUser(User user);

    Optional<ChallengeUser> findByUserAndChallenge(User user, Challenge challenge);

    int countByChallenge(Challenge challenge);

    @Query(value = """
        SELECT u.*
        FROM user u JOIN challenge_user cu ON cu.user_id = u.id
        WHERE u.deleted_at IS NULL
        AND cu.deleted_at IS NULL
        AND :challenge_id = cu.challenge_id
        AND u.id >= :cursor
        ORDER BY user_id
        LIMIT :limit
        """, nativeQuery = true)
    List<User> getParticipantList(@Param("challenge_id") Long id, @Param("cursor") Long cursor, @Param("limit") int limit);
}
