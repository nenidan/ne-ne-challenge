package com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.HistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SelectWinnerService {

    private static final Long TARGET_SUCCESS_RATE = 70L;

    private final HistoryRepository historyRepository;

    public List<Long> getWinners(Challenge challenge) {
        List<Long> participantIdList = challenge.getParticipantIdList();
        Map<Long, Integer> userHistoryCount = historyRepository.countHistory(participantIdList, challenge.getId());

        long totalDays = ChronoUnit.DAYS.between(challenge.getStartAt(), challenge.getDueAt()) + 1;
        List<Long> winners = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : userHistoryCount.entrySet()) {
            Long userId = entry.getKey();
            int successfulDays = entry.getValue().intValue();

            int successRate = (int) ((successfulDays * 100) / totalDays);
            if (successRate > TARGET_SUCCESS_RATE) {
                winners.add(userId);
            }
        }

        return winners;
    }
}
