package com.github.nenidan.ne_ne_challenge.domain.notification.service;

import org.springframework.stereotype.Service;

import com.github.nenidan.ne_ne_challenge.domain.notification.dto.request.SendNotificationRequest;

@Service
public interface NotificationService {
	Void send(SendNotificationRequest sendNotificationRequest);
}
