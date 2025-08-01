package com.github.nenidan.ne_ne_challenge.domain.notification.application.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.notification.application.sender.NotificationSender;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationLog;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationStatus;
import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.repository.jpa.JpaNotificationLogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationRetryService {

	private final JpaNotificationLogRepository notificationLogRepository;
	private final NotificationSender notificationSender;
	// 재전송 로직

	@Transactional
	public void retryNotifications() {
		List<NotificationLog> logs = notificationLogRepository.findByStatusAndCreatedAtAfter(NotificationStatus.WAITING, LocalDateTime.now().minusMinutes(10));

		for (NotificationLog log : logs) {
			boolean success = notificationSender.send(log.getUserId(), log.getPlatform(), log.getTitle(), log.getContent());

			if (success) {
				log.setStatus(NotificationStatus.SUCCESS);
			} else {
				log.increaseRetryCount();
				if (log.getRetryCount() >= 3) {
					log.setStatus(NotificationStatus.FAILED);
				}
			}

			notificationLogRepository.save(log);
		}
	}
}
