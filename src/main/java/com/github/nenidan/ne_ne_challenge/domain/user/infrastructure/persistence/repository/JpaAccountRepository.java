package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.AccountEntity;

public interface JpaAccountRepository extends JpaRepository<AccountEntity, Long> {

    boolean existsByEmail(String email);

    Optional<AccountEntity> findByEmail(String email);

}
