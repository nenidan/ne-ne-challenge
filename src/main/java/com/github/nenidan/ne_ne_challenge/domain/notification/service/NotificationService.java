package com.github.nenidan.ne_ne_challenge.domain.notification.service;

import org.springframework.stereotype.Service;

import com.github.nenidan.ne_ne_challenge.domain.notification.dto.request.ReadNotificationRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.dto.request.SendNotificationRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.dto.response.NotificationResponse;
import com.github.nenidan.ne_ne_challenge.domain.notification.enttiy.NotificationType;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import jakarta.validation.constraints.Min;

@Service
public interface NotificationService {
	Void send(SendNotificationRequest sendNotificationRequest);

	Void read(ReadNotificationRequest request, Long id);

	CursorResponse<NotificationResponse, Long> findNotifications(Long userId, String isRead, NotificationType type, Long cursor, @Min(1) int size);
}
