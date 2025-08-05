package com.github.nenidan.ne_ne_challenge.domain.notification.application.sender;

import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.fcm.Platform;

public interface NotificationSender {
	boolean send(Long userId, Platform platform, String title, String content);
}
