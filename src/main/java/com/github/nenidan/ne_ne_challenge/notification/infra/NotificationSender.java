package com.github.nenidan.ne_ne_challenge.notification.infra;

public interface NotificationSender {
	void send(Long userId, String title, String content);
}
