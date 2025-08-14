package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.repository;

import java.util.List;

import com.querydsl.core.types.dsl.Expressions;
import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.ProfileEntity;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.QAccountEntity;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.QProfileEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QProfieRepository {

    private final JPAQueryFactory queryFactory;

    QProfileEntity profile = QProfileEntity.profileEntity;
    QAccountEntity account = QAccountEntity.accountEntity;

//    public List<ProfileEntity> findByKeyword(String cursor, String keyword, int limit) {
//        BooleanExpression cursorCondition = cursor == null ? null : profile.nickname.goe(cursor);
//        BooleanExpression keywordCondition = keyword == null ? null : profile.nickname.startsWith(keyword);
//
//        return queryFactory
//                .selectFrom(profile)
//                .join(profile.account, account).fetchJoin()
//                .where(
//                        cursorCondition,
//                        keywordCondition
//                )
//                .orderBy(profile.nickname.asc())
//                .limit(limit)
//                .fetch();
//    }

    public List<ProfileEntity> findByKeyword(String cursor, String keyword, int limit) {
        BooleanExpression cursorCondition = cursor == null ? null : profile.nickname.goe(cursor);

        BooleanExpression keywordCondition = null;
        if (keyword != null) {
            keywordCondition = profile.nickname.contains(keyword)
                    .or(profile.account.email.contains(keyword))
                    .or(profile.bio.contains(keyword));
        }

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

    // 성능 비교용

    public List<ProfileEntity> findByKeyword2(String cursor, String keyword, int limit) {
        BooleanExpression cursorCondition = cursor == null ? null : profile.nickname.goe(cursor);

        BooleanExpression keywordCondition = null;
        if (keyword != null && !keyword.isEmpty()) {
            keywordCondition = Expressions.booleanTemplate(
                    "MATCH(nickname, bio) AGAINST ({0} IN BOOLEAN MODE)",
                    "+" + keyword + "*"
            );
        }

        return queryFactory
                .selectFrom(profile)
                .join(profile.account, account).fetchJoin()
                .where(cursorCondition, keywordCondition)
                .orderBy(profile.nickname.asc())
                .limit(limit)
                .fetch();
    }
}
