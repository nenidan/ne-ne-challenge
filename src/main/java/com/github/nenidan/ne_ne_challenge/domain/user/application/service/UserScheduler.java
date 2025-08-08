package com.github.nenidan.ne_ne_challenge.domain.user.application.service;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserScheduler {

    private final CachedUserService cachedUserService;

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        cachedUserService.refreshSearchProfiles();
        cachedUserService.refreshProfileAll();
    }

    @Scheduled(cron = "0 */4 * * * *")
    public void refreshSearchProfiles() {
        cachedUserService.refreshSearchProfiles();
    }

    @Scheduled(cron = "0 55 * * * *")
    public void refreshProfileAll() {
        cachedUserService.refreshProfileAll();
    }

}
