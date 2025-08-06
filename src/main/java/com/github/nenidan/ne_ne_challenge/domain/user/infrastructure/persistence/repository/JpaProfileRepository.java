package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.ProfileEntity;

public interface JpaProfileRepository extends JpaRepository<ProfileEntity, Long> {

    boolean existsByNickname(String nickname);

    @EntityGraph(attributePaths = {"account"})
    Optional<ProfileEntity> findByAccountEmail(String email);

    @EntityGraph(attributePaths = {"account"})
    Optional<ProfileEntity> findByAccount_KakaoId(String kakaoId);

    @EntityGraph(attributePaths = {"account"})
    Optional<ProfileEntity> findByAccount_NaverId(String naverId);

    @EntityGraph(attributePaths = {"account"})
    Optional<ProfileEntity> findByAccount_GoogleId(String googleId);

    @NonNull
    @EntityGraph(attributePaths = {"account"})
    Optional<ProfileEntity> findById(@NonNull Long id);

    @NonNull
    @EntityGraph(attributePaths = {"account"})
    List<ProfileEntity> findAll();

    @Query(value = """
    SELECT p.* FROM profile p
    JOIN account a ON p.id = a.id
    WHERE (:cursor IS NULL OR p.nickname >= :cursor)
    AND p.nickname LIKE CONCAT('%', :keyword, '%')
    ORDER BY p.nickname ASC
    LIMIT :limit
    """, nativeQuery = true)
    List<ProfileEntity> findByKeyword(
            @Param("cursor") String cursor,
            @Param("keyword") String keyword,
            @Param("limit") int limit
    );
}
