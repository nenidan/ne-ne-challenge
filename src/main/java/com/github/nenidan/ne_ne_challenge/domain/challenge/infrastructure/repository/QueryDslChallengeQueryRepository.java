package com.github.nenidan.ne_ne_challenge.domain.challenge.infrastructure.repository;

import static com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.QChallenge.*;
import static com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.QParticipant.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.request.ChallengeSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.repository.ChallengeQueryRepository;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QueryDslChallengeQueryRepository implements ChallengeQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ChallengeResponse> findChallengeById(Long challengeId) {
        ChallengeResponse result = projectToChallengeResponse()
            .from(challenge)
            .where(challenge.id.eq(challengeId).and(notSoftDeleted()))
            .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<ChallengeResponse> findChallenges(ChallengeSearchCond cond) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(userIdEq(cond.getUserId()));
        builder.and(nameContains(cond.getName()));
        builder.and(statusEq(cond.getStatus()));
        builder.and(dueAtEq(cond.getDueAt()));
        builder.and(categoryEq(cond.getCategory()));
        builder.and(maxParticipationFeeLoe(cond.getMaxParticipationFee()));
        builder.and(createdAtLoe(cond.getCursor()));
        builder.and(notSoftDeleted());

        return projectToChallengeResponse()
            .from(challenge)
            .join(challenge.participants, participant)
            .where(builder)
            .orderBy(challenge.createdAt.desc())
            .limit(cond.getSize() + 1)
            .fetch();
    }

    private JPAQuery<ChallengeResponse> projectToChallengeResponse() {
        return queryFactory
            .select(Projections.bean(ChallengeResponse.class,
                challenge.id,
                challenge.name,
                challenge.description,
                challenge.status,
                challenge.category,
                challenge.minParticipants,
                challenge.maxParticipants,
                challenge.participationFee,
                challenge.totalFee,
                challenge.dueAt,
                challenge.startAt,
                challenge.createdAt,
                challenge.updatedAt,
                challenge.deletedAt
            ));
    }

    private BooleanExpression notSoftDeleted() {
        return challenge.deletedAt.isNull();
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId == null ? null : participant.userId.eq(userId);
    }

    private BooleanExpression nameContains(String name) {
        return (name == null || name.isBlank()) ? null : challenge.name.containsIgnoreCase(name);
    }

    private BooleanExpression statusEq(ChallengeStatus status) {
        return status == null ? null : challenge.status.eq(status);
    }

    private BooleanExpression dueAtEq(LocalDate dueAt) {
        return dueAt == null ? null : challenge.dueAt.eq(dueAt);
    }

    private BooleanExpression createdAtLoe(LocalDateTime cursor) {
        return cursor == null ? null : challenge.createdAt.loe(cursor);
    }

    private BooleanExpression categoryEq(ChallengeCategory category) {
        return category == null ? null : challenge.category.eq(category);
    }

    private BooleanExpression maxParticipationFeeLoe(Integer participationFee) {
        return participationFee == null ? null : challenge.participationFee.loe(participationFee);
    }
}
