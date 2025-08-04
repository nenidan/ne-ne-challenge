package com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.redis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.request.RetryNotificationRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationLog;

@Repository
public interface RedisNotificationRepository {
	void push(NotificationLog log, int delaySeconds);

	List<RetryNotificationRequest> getNotifications(long now);
}
