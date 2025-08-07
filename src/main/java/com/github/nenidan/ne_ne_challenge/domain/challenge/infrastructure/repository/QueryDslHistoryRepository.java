package com.github.nenidan.ne_ne_challenge.domain.challenge.infrastructure.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.request.HistorySearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response.ChallengeHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.repository.ChallengeHistoryQueryRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.QHistory.history;

@Repository
@RequiredArgsConstructor
public class QueryDslHistoryRepository implements ChallengeHistoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ChallengeHistoryResponse> findHistoryById(Long historyId) {
        ChallengeHistoryResponse result = projectToChallengeHistoryResponse()
            .from(history)
            .where(history.id.eq(historyId).and(notSoftDeleted()))
            .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<ChallengeHistoryResponse> findHistory(Long challengeId, HistorySearchCond cond) {
        return projectToChallengeHistoryResponse()
            .from(history)
            .where(
                history.challengeId.eq(challengeId)
                    .and(userIdEq(cond.getUserId()))
                    .and(createdAtLoe(cond.getCursor()))
            )
            .orderBy(history.createdAt.desc())
            .limit(cond.getSize() + 1)
            .fetch();
    }

    @Override
    public Long countByChallengeIdAndUserId(Long challengeId, Long userId) {
        return queryFactory.select(history.count())
            .from(history)
            .where(history.challengeId.eq(challengeId)
                .and(history.userId.eq(userId))
                .and(history.isSuccess.isTrue())
                .and(notSoftDeleted()))
            .fetchOne();
    }

    private JPAQuery<ChallengeHistoryResponse> projectToChallengeHistoryResponse() {
        return queryFactory
            .select(Projections.constructor(ChallengeHistoryResponse.class,
                history.id,
                history.userId,
                history.challengeId,
                history.content,
                history.isSuccess,
                history.date,
                history.createdAt,
                history.updatedAt,
                history.deletedAt
            ));
    }

    private BooleanExpression notSoftDeleted() {
        return history.deletedAt.isNull();
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId == null ? null : history.userId.eq(userId);
    }

    private BooleanExpression createdAtLoe(LocalDateTime cursor) {
        return cursor == null ? null : history.createdAt.loe(cursor);
    }
}
