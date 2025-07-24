package com.github.nenidan.ne_ne_challenge.domain.challenge.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.ChallengeUser;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeUserRepository extends JpaRepository<ChallengeUser, Long> {

    List<ChallengeUser> findByUser(User user);

    Optional<ChallengeUser> findByUserAndChallenge(User user, Challenge challenge);

    int countByChallenge(Challenge challenge);
}
