package com.github.nenidan.ne_ne_challenge.domain.notification.service;

import org.springframework.stereotype.Service;

import com.github.nenidan.ne_ne_challenge.domain.notification.dto.request.NotificationRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.entity.Notification;
import com.github.nenidan.ne_ne_challenge.domain.notification.repository.NotificationRepository;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserException;
import com.github.nenidan.ne_ne_challenge.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InternalNotificationService implements NotificationService {
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
	public void send(NotificationRequest notificationRequest) {
		User receiver = userRepository.findById(notificationRequest.getReceiverId())
			.orElseThrow(() -> new UserException(UserErrorCode.INVALID_USER_ROLE)); // TODO : 받는 사용자가 없다는 에러가 없어서 임시 에러로 대체

		User sender = null;
		if (notificationRequest.getSenderId() != null) {
			sender = userRepository.findById(notificationRequest.getSenderId())
				.orElseThrow(() -> new UserException(UserErrorCode.INVALID_USER_ROLE)); // TODO : 보내는 사용자 ID는 있지만, 존재하지 않는 경우가 없어 임시 에러로 대체
		}

		Notification notification = new Notification(
			notificationRequest.getTitle(),
			notificationRequest.getContent(),
			notificationRequest.getNotificationType(),
			receiver,
			sender
		);
		notificationRepository.save(notification);
	}

}
