package com.github.nenidan.ne_ne_challenge.domain.notification.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.request.RetryNotificationRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.application.sender.NotificationSender;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationLog;
import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.fcm.Platform;
import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.redis.RedisNotificationRepository;
import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.repository.jpa.JpaNotificationLogRepository;

@ExtendWith(MockitoExtension.class)
class NotificationRetryServiceTest {

	@InjectMocks
	NotificationRetryService retryService;

	@Mock
	RedisNotificationRepository redisNotificationRepository;

	@Mock
	JpaNotificationLogRepository logRepository;

	@Mock
	NotificationSender sender;

	@Test
	@DisplayName("조회_성능_측정")
	void test() throws InterruptedException {
		// given
		int taskCount = 300;
		CountDownLatch latch = new CountDownLatch(taskCount);

		List<RetryNotificationRequest> dtoList = IntStream.range(0, taskCount)
			.mapToObj(i -> new RetryNotificationRequest((long)i, 100L + i, "title", "content", Platform.WEB, 0))
			.toList();

		when(redisNotificationRepository.getNotifications(anyLong())).thenReturn(dtoList);
		when(sender.send(any(), any(), any(), any())).thenReturn(true);
		when(logRepository.findById(anyLong())).thenAnswer(invocation -> {
			Long id = invocation.getArgument(0);
			NotificationLog log = new NotificationLog();
			ReflectionTestUtils.setField(log, "id", id);
			return Optional.of(log);
		});

		// save 호출될 때마다 latch 감소
		doAnswer(invocation -> {
			latch.countDown();
			return null;
		}).when(logRepository).save(any(NotificationLog.class));

		// 시간 측정 시작
		long start = System.currentTimeMillis();

		// when
		retryService.retry();

		// 병렬 작업이 모두 끝날 때까지 대기
		boolean completed = latch.await(10, TimeUnit.SECONDS);

		// 시간 측정 종료
		long end = System.currentTimeMillis();

		// then
		assertTrue(completed, "병렬 작업이 제한 시간 내 완료되지 않았음");
		System.out.println("\n조회 결과 = "+taskCount+"건, 소요시간 = "+ (end - start) + " ms\n");

	}
}