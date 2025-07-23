package com.github.nenidan.ne_ne_challenge.domain.notification.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.notification.dto.request.ReadNotificationRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.dto.request.SendNotificationRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.dto.response.NotificationResponse;
import com.github.nenidan.ne_ne_challenge.domain.notification.enttiy.Notification;
import com.github.nenidan.ne_ne_challenge.domain.notification.enttiy.NotificationType;
import com.github.nenidan.ne_ne_challenge.domain.notification.exception.NotificationErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.notification.exception.NotificationException;
import com.github.nenidan.ne_ne_challenge.domain.notification.repository.NotificationRepository;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserException;
import com.github.nenidan.ne_ne_challenge.domain.user.repository.UserRepository;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InternalNotificationServiceImpl implements NotificationService {
	private final NotificationRepository notificationRepository;
	private final UserRepository userRepository;

	/*
		알림을 저장하는 기능
		메서드명이 send 인 이유 ?
		- 다른 도메인에서 서비스를 가져가서 notificationService.send(...) 하여 알림을 보낸다고 생각.
		- 알림은 받은 사람은 알림이 온게 아닌, 지금은 메일처럼 DB에 저장되는 형식임.
		- 알림을 확인하려면 DB 에서 호출해야됨.
	 */
	@Transactional
	@Override
	public Void send(SendNotificationRequest notificationRequest) {
		User receiver = userRepository.findById(notificationRequest.getReceiverId())
			.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

		User sender = null;
		if (notificationRequest.getSenderId() != null) {
			sender = userRepository.findById(notificationRequest.getSenderId())
				.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
		}

		Notification notification = new Notification(
			notificationRequest.getTitle(),
			notificationRequest.getContent(),
			notificationRequest.getNotificationType(),
			receiver,
			sender
		);
		notificationRepository.save(notification);
		return null;
	}

	@Transactional
	@Override
	public Void read(ReadNotificationRequest request, Long id) {
		Notification notification = notificationRepository.findById(id)
			.orElseThrow(() -> new NotificationException(NotificationErrorCode.NOT_FOUND));

		notification.setRead(request.isRead());

		return null;
	}

	@Override
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
				notifications = notificationRepository.findAllByUserIdAndIsReadWithType(
					userId, false, type.name(), cursorId, size + 1);
			}
			case "true" -> {
				notifications = notificationRepository.findAllByUserIdAndIsReadWithType(
					userId, true, type.name(), cursorId, size + 1);
			}
			default -> {
				notifications = notificationRepository.findAllByUserIdWithType(
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
