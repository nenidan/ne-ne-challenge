package com.github.nenidan.ne_ne_challenge.domain.point.domain.service;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.point.domain.model.Point;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.model.PointWallet;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.repository.PointRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PointDomainService {

    private final PointRepository pointRepository;

    public void usePointsWithFifo(PointWallet pointWallet, int amount) {

        // 포인트 지갑에 있는 돈보다 요청 금액이 많으면 예외가 터진다.
        pointWallet.validateSufficientBalance(amount);

        // 남아있는 돈이 0보다 크거나 취소되지 않은 Point의 리스트를 불러옴
        List<Point> pointList = pointRepository.findUsablePointsByWalletId(pointWallet.getId());

        int originalAmount = amount;
        int remainingAmount = amount;

        // 그 리스트에서 하나씩 돌면서
        for (Point point : pointList) {
            // 그 포인트에서 사용가능한 포인트가 얼마인지
            int availableBalance = point.getRemainingAmount();

            // 만약에 사용가능한 포인트가 amount 보다 크거나 같다면 바로 for문 탈출
            if (availableBalance >= remainingAmount) {
                point.decrease(remainingAmount);
                point.markUsed();
                break;
            } else {
                // 그렇지 않다면
                point.decrease(availableBalance); // 남아있는 만큼만 차감
                point.markUsed();
                remainingAmount -= availableBalance; // 차감 후 남은 금액을 다음 포인트에서 이어서 차감
            }
        }
        pointWallet.decrease(originalAmount);
    }
}
