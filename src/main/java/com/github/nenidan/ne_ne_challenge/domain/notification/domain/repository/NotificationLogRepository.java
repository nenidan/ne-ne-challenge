package com.github.nenidan.ne_ne_challenge.domain.notification.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationLog;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationStatus;

@Repository
public interface NotificationLogRepository {
	List<NotificationLog> findRetryTargets(NotificationStatus status, LocalDateTime localDateTime);
	void save(NotificationLog notificationLog);
}
