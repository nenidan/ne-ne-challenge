package com.github.nenidan.ne_ne_challenge.notification.infra;

import com.github.nenidan.ne_ne_challenge.notification.infra.fcm.Platform;

public interface NotificationSender {
	void send(Long userId, Platform platform, String title, String content);
}
