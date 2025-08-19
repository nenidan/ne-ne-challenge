package com.github.nenidan.ne_ne_challenge.global.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.request.SendNotificationRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.application.service.NotificationService;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.NotificationType;
import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.fcm.Platform;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.event.PaymentCompletedEvent;
import com.github.nenidan.ne_ne_challenge.global.client.user.UserClient;
import com.github.nenidan.ne_ne_challenge.global.client.user.dto.UserResponse;
import com.github.nenidan.ne_ne_challenge.global.event.ChallengeClearedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {
	private final NotificationService notificationService;
	private final UserClient userClient;

	@Async
	@EventListener
	/*
    리스너 설명 및 주요 옵션

    @EventListener
    - 기본적인 동기 이벤트 처리에 사용
    - 트랜잭션과는 무관하게 이벤트를 처리할 때 사용

    @Async
    - 이벤트 처리를 비동기로 수행 (별도 스레드에서 실행됨)
    - FCM 전송, 이메일 전송 등 시간이 오래 걸리는 작업에 적합
    - 전제조건: @EnableAsync 설정 필요 <- 고도화 작업에 사용할 예정

    @TransactionalEventListener
    ex> @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    - 트랜잭션 상태에 따라 이벤트 처리 시점을 제어함
    - phase 옵션:
        - BEFORE_COMMIT: 커밋 전에 실행
        - AFTER_COMMIT (기본): 커밋 후 실행
        - AFTER_ROLLBACK: 롤백 후 실행
        - AFTER_COMPLETION: 커밋 또는 롤백 후 항상 실행
    - 주의: 트랜잭션 외부에서 publishEvent 하면 무시됨
	*/
	public void handle(ChallengeClearedEvent event) {
		UserResponse userResponse = userClient.getUserById(event.getUserId());

		String title = " 챌린지 완료!";
		String content = userResponse.getNickname() + "님이 첼린지를 완료했습니다.";

		notificationService.send(new SendNotificationRequest(
			title, content,
			NotificationType.CHALLENGE_ENDED,
			event.getUserId(), null, Platform.WEB
		));
	}

	@Async
	@EventListener
	public void handlePaymentCompleted(PaymentCompletedEvent event) {
		UserResponse userResponse = userClient.getUserById(event.getUserId());

		String title = "결제 완료!";
		String content = userResponse.getNickname() + "님이 " + event.getAmount() + "원 결제가 완료되었습니다.";

		notificationService.send(new SendNotificationRequest(
			title, content,
			NotificationType.PAYMENT_SUCCESS,
			event.getUserId(), null, Platform.WEB
		));
	}
}