package com.github.nenidan.ne_ne_challenge.domain.user.integration;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.user.domain.type.Role;

@SpringBootTest
public class UserBulkInsertTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Disabled
    @Test
    @Transactional
    @Rollback(false)
    public void insertUsersTest() {
        int batchSize = 1_000;
        List<Object[]> params1 = new ArrayList<>();
        List<Object[]> params2 = new ArrayList<>();

        for (int i = 0; i < 500_000; i++) {
            params1.add(new Object[]{
                    i + 1L,
                    "gajicoding"+i + "@gmail.com",
                    "$2a$10$TtnFitIRH2TzeYTPFjwPeui1wkAKRuO/VU0poiDEgjznJI6A6EZh6",
                    Role.USER.name()
            });

            params2.add(new Object[]{
                    i + 1L,
                    "gajicoding"+i,
                    "가지가지하네"+i,
                    "2000-01-01"
            });

            if (params1.size() == batchSize) {
                jdbcTemplate.batchUpdate(
                        "INSERT INTO account (id, email, password, role, created_at, updated_at) VALUES (?, ?, ?, ?, NOW(), NOW())",
                        params1
                );
                jdbcTemplate.batchUpdate(
                        "INSERT INTO profile (id, nickname, bio, birth, created_at, updated_at) VALUES (?, ?, ?, ?, NOW(), NOW())",
                        params2
                );

                params1.clear();
                params2.clear();
            }
        }

        if (!params1.isEmpty()) {
            jdbcTemplate.batchUpdate(
                    "INSERT INTO account (id, email, password, role, created_at, updated_at) VALUES (?, ?, ?, ?, NOW(), NOW())",
                    params1
            );
            params1.clear();
        }

        if (!params2.isEmpty()) {
            jdbcTemplate.batchUpdate(
                    "INSERT INTO profile (id, nickname, bio, birth, created_at, updated_at) VALUES (?, ?, ?, ?, NOW(), NOW())",
                    params2
            );
            params2.clear();
        }
    }
}
