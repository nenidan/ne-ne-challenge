package com.github.nenidan.ne_ne_challenge.domain.notification.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.domain.notification.application.NotificationRetryService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationRetryScheduler {
	private final NotificationRetryService retryService;

	@Scheduled(fixedDelay = 5*1000)
	public void retryNotifications(){
		retryService.retryNotifications();
	}
}
