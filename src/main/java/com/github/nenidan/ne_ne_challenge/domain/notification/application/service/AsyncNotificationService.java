package com.github.nenidan.ne_ne_challenge.domain.notification.application.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.notification.application.sender.NotificationSender;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.Notification;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationLog;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationStatus;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.repository.NotificationLogRepository;
import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.fcm.Platform;
import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.redis.RedisNotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsyncNotificationService {
	private final NotificationLogRepository notificationLogRepository;
	private final RedisNotificationRepository redisNotificationRepository;
	private final NotificationSender notificationSender;

	@Async
	@Transactional
	public void sendAndLog(Notification notification, Platform platform) {
		boolean success = notificationSender.send(
			notification.getReceiver().getId(),
			platform,
			notification.getTitle(),
			notification.getContent()
		);

		NotificationLog log = new NotificationLog(
			notification.getReceiver().getId(),
			notification.getTitle(),
			notification.getContent(),
			platform,
			success ? NotificationStatus.SUCCESS : NotificationStatus.WAITING
		);

		notificationLogRepository.save(log);

		if (!success) {
			redisNotificationRepository.push(log, 5);
		}
	}

}
