package com.github.nenidan.ne_ne_challenge.notification.infra.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.notification.domain.entity.Notification;
import com.github.nenidan.ne_ne_challenge.notification.domain.entity.NotificationType;
import com.github.nenidan.ne_ne_challenge.notification.domain.entity.QNotification;
import com.github.nenidan.ne_ne_challenge.notification.domain.repository.QNotificationRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class QNotificationImpl implements QNotificationRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Notification> findAllByUserIdAndIsReadWithType(
		Long userId,
		boolean isRead,
		String type,
		Long cursorId,
		int size
	) {
		QNotification n = QNotification.notification;

		BooleanBuilder builder = new BooleanBuilder();
		builder.and(n.receiver.id.eq(userId));
		builder.and(n.isRead.eq(isRead));

		if (cursorId != null) {
			builder.and(n.id.gt(cursorId));
		}

		if (!"ALL".equals(type)) {
			builder.and(n.type.eq(NotificationType.valueOf(type)));
		}

		return jpaQueryFactory
			.selectFrom(n)
			.where(builder)
			.orderBy(n.id.asc())
			.limit(size)
			.fetch();
	}

	@Override
	public List<Notification> findAllByUserIdWithType(Long userId, String type, Long cursorId, int size) {
		QNotification n = QNotification.notification;

		BooleanBuilder builder = new BooleanBuilder();

		builder.and(n.receiver.id.eq(userId));

		if (cursorId != null) {
			builder.and(n.id.gt(cursorId));
		}

		if (!"ALL".equals(type)) {
			builder.and(n.type.eq(NotificationType.valueOf(type)));
		}

		return jpaQueryFactory
			.selectFrom(n)
			.where(builder)
			.orderBy(n.id.asc())
			.limit(size)
			.fetch();
	}

}
