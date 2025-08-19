package com.github.nenidan.ne_ne_challenge.domain.user.integration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import net.datafaker.Faker;

import com.github.nenidan.ne_ne_challenge.domain.user.domain.type.Role;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.type.Sex;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.search.document.UserDocument;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.search.repository.UserDocumentRepository;

@SpringBootTest
public class UserBulkInsertTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDocumentRepository userDocumentRepository;

    @Disabled
    @Test
    @Transactional
    @Rollback(false)
    public void insertUsersTest() {
        int batchSize = 1_000;
        List<Object[]> params1 = new ArrayList<>();
        List<Object[]> params2 = new ArrayList<>();

        List<UserDocument> userDocuments = new ArrayList<>();

        Faker faker = new Faker(new Locale("ko"));
        Faker fakerEng = new Faker();

        List<Sex> sexes = List.of(Sex.MALE, Sex.FEMALE);

        String now = LocalDateTime.now().toString();

        for (int i = 0; i < 1_000_000; i++) {
            String email = fakerEng.name().firstName().toLowerCase() + i + "@" + fakerEng.internet().domainName();
            String nickname = faker.name().firstName() + i;
            String bio = faker.lorem().sentence();
            String birth = randomBirthDate();
            String sex = sexes.get(i%2).name();

            params1.add(new Object[]{
                    i + 1L,
                    email,
                    "$2a$10$TtnFitIRH2TzeYTPFjwPeui1wkAKRuO/VU0poiDEgjznJI6A6EZh6",
                    Role.USER.name()
            });

            params2.add(new Object[]{
                    i + 1L,
                    nickname,
                    bio,
                    birth,
                    sex,
            });

            // Elastic Search 데이터
            UserDocument doc = new UserDocument(
                    String.valueOf(i + 1L),
                    email,
                    Role.USER.name(),
                    nickname,
                    Integer.parseInt(birth.substring(0,4)),
                    birth.substring(5),
                    sex,
                    bio,
                    now,
                    now,
                    null
            );

            userDocuments.add(doc);


            if (params1.size() == batchSize) {
                jdbcTemplate.batchUpdate(
                        "INSERT INTO account (id, email, password, role, created_at, updated_at) VALUES (?, ?, ?, ?, NOW(), NOW())",
                        params1
                );
                jdbcTemplate.batchUpdate(
                        "INSERT INTO profile (id, nickname, bio, birth, sex, created_at, updated_at) VALUES (?, ?, ?, ?, ?, NOW(), NOW())",
                        params2
                );
                userDocumentRepository.saveAll(userDocuments);

                params1.clear();
                params2.clear();
                userDocuments.clear();
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


        if (!userDocuments.isEmpty()) {
            userDocumentRepository.saveAll(userDocuments);
            userDocuments.clear();
        }
    }

    private String randomBirthDate() {
        long start = java.sql.Date.valueOf("1980-01-01").getTime();
        long end = java.sql.Date.valueOf("2005-12-31").getTime();
        long random = ThreadLocalRandom.current().nextLong(start, end);
        return new java.sql.Date(random).toString(); // yyyy-MM-dd 형식
    }
}
