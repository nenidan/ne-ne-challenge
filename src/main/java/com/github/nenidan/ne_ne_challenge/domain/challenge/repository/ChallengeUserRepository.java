package com.github.nenidan.ne_ne_challenge.domain.challenge.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.ChallengeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeUserRepository extends JpaRepository<ChallengeUser, Long> {

}
