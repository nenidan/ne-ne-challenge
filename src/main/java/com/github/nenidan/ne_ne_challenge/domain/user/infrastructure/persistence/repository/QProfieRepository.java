package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.repository;

import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.ProfileEntity;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.QAccountEntity;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.QProfileEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QProfieRepository {

    private final JPAQueryFactory queryFactory;

    QProfileEntity profile = QProfileEntity.profileEntity;
    QAccountEntity account = QAccountEntity.accountEntity;

    public List<ProfileEntity> findByKeyword(String cursor, String keyword, int limit) {
        BooleanExpression cursorCondition = cursor == null ? null : profile.nickname.goe(cursor);
        BooleanExpression keywordCondition = keyword == null ? null : profile.nickname.startsWith(keyword);

        return queryFactory
                .selectFrom(profile)
                .join(profile.account, account).fetchJoin()
                .where(
                        cursorCondition,
                        keywordCondition
                )
                .orderBy(profile.nickname.asc())
                .limit(limit)
                .fetch();
    }
}
