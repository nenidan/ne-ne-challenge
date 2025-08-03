package com.github.nenidan.ne_ne_challenge.domain.user.domain.repository;

import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    List<User> findByKeyword(String cursor, String keyword, int limit);

    User update(User user);

    Optional<User> findByKakaoId(String kakaoId);

    Optional<User> findByNaverId(String naverId);

    Optional<User> findByGoogleId(String googleId);

    void delete(User user);
}
