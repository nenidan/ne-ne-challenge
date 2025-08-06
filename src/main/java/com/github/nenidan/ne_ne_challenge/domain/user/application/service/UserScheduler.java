package com.github.nenidan.ne_ne_challenge.domain.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserScheduler {

    private final CachedUserService cachedUserService;

    @Scheduled(cron = "0 */4 * * * *")
    public void refreshSearchProfiles() {
        cachedUserService.refreshSearchProfiles();
    }

}
