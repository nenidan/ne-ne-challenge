package com.github.nenidan.ne_ne_challenge.domain.notification.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.request.ReadNotificationRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.request.SendNotificationRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.response.NotificationResponse;
import com.github.nenidan.ne_ne_challenge.domain.notification.application.sender.NotificationSender;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.Notification;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.NotificationType;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationLog;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationStatus;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.exception.NotificationErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.exception.NotificationException;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.repository.NotificationLogRepository;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.repository.NotificationRepository;
import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.redis.RedisNotificationRepository;
import com.github.nenidan.ne_ne_challenge.global.client.user.UserClient;
import com.github.nenidan.ne_ne_challenge.global.client.user.dto.UserResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {
	private final NotificationRepository notificationRepository;
	private final NotificationLogRepository notificationLogRepository;
	private final RedisNotificationRepository redisNotificationRepository;

	private final NotificationSender notificationSender;
	private final UserClient client;

	@Transactional
	public Void send(SendNotificationRequest notificationRequest) {
		UserResponse receiver = client.getUserById(notificationRequest.getReceiverId());

		UserResponse sender = null;
		if (notificationRequest.getSenderId() != null) {
			sender = client.getUserById(notificationRequest.getSenderId());
		}

		Notification notification = new Notification(
			notificationRequest.getTitle(),
			notificationRequest.getContent(),
			notificationRequest.getNotificationType(),
			receiver.getId(),
			sender == null ? null : sender.getId()
		);

		boolean b = notificationSender.send(
			receiver.getId(),
			notificationRequest.getPlatform(),
			notification.getTitle(),
			notification.getContent()
		);

		NotificationLog notificationLog = new NotificationLog(
			receiver.getId(),
			notification.getTitle(),
			notification.getContent(),
			notificationRequest.getPlatform(),
			b ? NotificationStatus.SUCCESS : NotificationStatus.WAITING);

		notificationLogRepository.save(notificationLog); // 사용자에게 보내려 했던 알림 자체 ( 원본 기록 ) 무조건 기록
		notificationRepository.save(notification); // 전송 성공/실패 여부, 재시도 횟수 관리 무조건 기록

		if (!b) {
			redisNotificationRepository.push(notificationLog, 5);
		}

		return null;
	}

	@Transactional
	public Void read(ReadNotificationRequest request, Long id) {
		Notification notification = notificationRepository.findById(id)
			.orElseThrow(() -> new NotificationException(NotificationErrorCode.NOT_FOUND));

		notification.setRead(request.isRead());

		return null;
	}

	public CursorResponse<NotificationResponse, Long> findNotifications(
		Long userId,
		String isRead,
		NotificationType type,
		Long cursor,
		int size
	) {
		List<Notification> notifications;

		Long cursorId = cursor == null ? 0L : cursor;

		switch (isRead) { // boolean 타입은 true/ false 만 있어서 메일 읽음/안읽음 처리만 됨 그래서 String 으로 받아서 전체 조회도 가능하게 설정
			case "false" -> {
				notifications = notificationRepository.searchByUserIdAndIsReadWithType(
					userId, false, type.name(), cursorId, size + 1);
			}
			case "true" -> {
				notifications = notificationRepository.searchByUserIdAndIsReadWithType(
					userId, true, type.name(), cursorId, size + 1);
			}
			default -> {
				notifications = notificationRepository.searchByUserIdAndType(
					userId, type.name(), cursorId, size + 1);
			}
		}

		List<NotificationResponse> content = notifications.stream()
			.map(NotificationResponse::from)
			.toList();

		boolean hasNext = content.size() > size;
		List<NotificationResponse> result = hasNext ? content.subList(0, size) : content;
		Long nextCursor = hasNext ? content.get(size - 1).getId() : null;

		return new CursorResponse<>(result, nextCursor, hasNext);
	}
}
