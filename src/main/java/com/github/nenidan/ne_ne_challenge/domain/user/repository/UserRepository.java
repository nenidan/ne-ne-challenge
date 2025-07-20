package com.github.nenidan.ne_ne_challenge.domain.user.repository;

import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
