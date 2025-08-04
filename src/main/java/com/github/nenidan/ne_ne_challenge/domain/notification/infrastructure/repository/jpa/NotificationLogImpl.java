package com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.repository.jpa;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationLog;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationStatus;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.repository.NotificationLogRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NotificationLogImpl implements NotificationLogRepository {

	private final JpaNotificationLogRepository jpaNotificationLogRepository;

	@Override
	public List<NotificationLog> findRetryTargets(NotificationStatus status, LocalDateTime limit) {
		return jpaNotificationLogRepository.findByStatusAndCreatedAtAfter(status, limit);
	}

	@Override
	public void save(NotificationLog notificationLog) {
		jpaNotificationLogRepository.save(notificationLog);
	}
}
