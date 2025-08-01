package com.github.nenidan.ne_ne_challenge.global.client.point;


public interface PointClient {

    /**
     * 사용자의 포인트 지갑을 생성합니다.
     * 회원가입 시 호출됩니다.
     *
     * @param userId 포인트 지갑을 생성할 사용자 ID
     */
    void createPointWallet(Long userId);

    /**
     * 결제를 통해 포인트를 충전합니다.
     * 사용자가 실제 돈을 지불하여 포인트를 구매할 때 사용됩니다.
     *
     * @param userId 포인트를 충전할 사용자 ID
     * @param amount 충전할 포인트 금액 (양수)
     * @param reason 충전 사유 (예: "CHARGE")
     */
    void chargePoint(Long userId, int amount, String reason, String orderId);

    /**
     * 사용자가 자신의 ID를 입력하여 포인트 지갑에 포인트가 얼마 있는지 확인하는 내부용 메서드입니다.
     * @param userId 포인트를 조회할 사용자 ID
     * @return 현재 가지고있는 포인트
     */
    PointBalanceResponse getMyBalance(Long userId);

    /**
     * 시스템에서 사용자에게 포인트를 지급합니다.
     * 챌린지 보상 등으로 포인트를 적립할 때 사용됩니다.
     *
     * @param userId 포인트를 지급받을 사용자 ID
     * @param amount 지급할 포인트 금액 (양수)
     * @param reason 지급 사유 (예: "CHALLENGE_REWARD")
     */
    void increasePoint(Long userId, int amount, String reason);

    /**
     * 사용자의 포인트를 차감합니다.
     * 상점 구매, 챌린지 참가비, 패널티 등으로 포인트를 사용할 때 호출됩니다.
     *
     * @param userId 포인트를 차감할 사용자 ID
     * @param amount 차감할 포인트 금액 (양수)
     * @param reason 차감 사유 (예: "SHOP_PURCHASE", "CHALLENGE_ENTRY")
     */
    void decreasePoint(Long userId, int amount, String reason);

    void cancelPoint(String orderId);
}
