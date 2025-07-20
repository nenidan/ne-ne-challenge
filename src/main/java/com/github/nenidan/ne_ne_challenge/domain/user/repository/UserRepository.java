package com.github.nenidan.ne_ne_challenge.domain.user.repository;

import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM user u " +
            "WHERE (:cursor IS NULL OR u.nickname >= :cursor) " +
            "AND u.nickname LIKE CONCAT('%', :keyword, '%') " +
            "ORDER BY u.nickname ASC " +
            "LIMIT :limit",
            nativeQuery = true)
    List<User> findByKeword(
            @Param("cursor") String cursor,
            @Param("keyword") String keyword,
            @Param("limit") int limit
    );
}
