package com.github.nenidan.ne_ne_challenge.domain.notification.application.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.request.RetryNotificationRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.application.sender.NotificationSender;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationLog;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationStatus;
import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.redis.RedisNotificationRepository;
import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.repository.jpa.JpaNotificationLogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationRetryService {

	private final NotificationSender sender;
	private final RedisNotificationRepository redisNotificationRepository;
	private final JpaNotificationLogRepository logRepository;

	public void retry() {
		long now = System.currentTimeMillis();
		List<RetryNotificationRequest> retryList = redisNotificationRepository.getNotifications(now);
		ExecutorService executor = Executors.newFixedThreadPool(5);

		for (RetryNotificationRequest dto : retryList) {
			final RetryNotificationRequest task = dto;
			executor.submit(() -> {
				try {
					boolean success = sender.send(
						task.getReceiverId(),
						task.getPlatform(),
						task.getTitle(),
						task.getContent()
					);

					Optional<NotificationLog> logOpt = logRepository.findById(task.getNotificationLogId());
					if (logOpt.isEmpty()) return;

					NotificationLog log = logOpt.get();

					if (success) {
						log.setStatus(NotificationStatus.SUCCESS);
					} else {
						log.increaseRetryCount();
						if (log.getRetryCount() >= 3) {
							log.setStatus(NotificationStatus.FAILED);
						} else {
							log.setStatus(NotificationStatus.WAITING);
							redisNotificationRepository.push(log, 10);
						}
					}

					logRepository.save(log);

				} catch (Exception e) {
					log.error("알림 재전송 실패", e);
				}
			});
		}

		executor.shutdown();
	}
}
