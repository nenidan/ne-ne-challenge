package com.github.nenidan.ne_ne_challenge.domain.notification.service;

import org.springframework.stereotype.Service;

import com.github.nenidan.ne_ne_challenge.domain.notification.dto.request.ReadNotificationRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.dto.request.SendNotificationRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.enttiy.Notification;
import com.github.nenidan.ne_ne_challenge.domain.notification.exception.NotificationErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.notification.exception.NotificationException;
import com.github.nenidan.ne_ne_challenge.domain.notification.repository.NotificationRepository;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserException;
import com.github.nenidan.ne_ne_challenge.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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

	@Override
	public Void read(ReadNotificationRequest request, Long id) {
		Notification notification = notificationRepository.findById(id)
			.orElseThrow(()->new NotificationException(NotificationErrorCode.NOT_FOUND));

		notification.setRead(request.isRead());

		return null;
	}
}
