package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.entity.AopLog;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.respository.LogTransactionRepository;
import com.github.nenidan.ne_ne_challenge.global.aop.event.AdminActionLoggedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminLogEventListener {

    private final LogTransactionRepository logTransactionRepository;

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAdminActionLogged(AdminActionLoggedEvent event) {
        AopLog log = new AopLog(
                event.type(),
                event.targetId(),
                event.method(),
                event.params(),
                event.result(),
                event.success()
        );

        logTransactionRepository.save(log);
    }

}
