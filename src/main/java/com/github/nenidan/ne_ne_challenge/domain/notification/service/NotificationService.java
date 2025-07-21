package com.github.nenidan.ne_ne_challenge.domain.notification.service;

import com.github.nenidan.ne_ne_challenge.domain.notification.dto.request.NotificationRequest;

public interface NotificationService {
	void send(NotificationRequest notificationRequest);
}
