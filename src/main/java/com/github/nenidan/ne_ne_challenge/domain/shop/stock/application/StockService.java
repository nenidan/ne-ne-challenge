package com.github.nenidan.ne_ne_challenge.domain.shop.stock.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto.AddStockCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto.AddStockResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.Stock;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.StockRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    /**
     * 특정 상품에 대한 초기 재고를 생성합니다.
     *
     * @param productId 재고를 생성할 상품 ID
     * @author kimyongjun0129
     */
    @Transactional
    public void createStock(Long productId) {
        stockRepository.save(new ProductId(productId));
    }

    /**
     * 재고를 증가시킵니다.
     *
     * @param addStockCommand 재고 증가 명령(상품 ID, 증가 수량)
     * @return 증가된 재고 정보 DTO
     * @author kimyongjun0129
     */
    @Transactional
    public AddStockResult increaseStock(AddStockCommand addStockCommand) {
        Stock stock = stockRepository.increase(addStockCommand.getProductId(), addStockCommand.getQuantity());
        return AddStockResult.from(stock);
    }

    /**
     * 재고를 감소시킵니다.
     *
     * @param addStockCommand 재고 감소 명령(상품 ID, 감소 수량)
     * @author kimyongjun0129
     */
    @Transactional
    public void decreaseStock(AddStockCommand addStockCommand) {
        stockRepository.decrease(addStockCommand.getProductId(), addStockCommand.getQuantity());
    }

    /**
     * 특정 상품의 재고 정보를 조회합니다.
     *
     * @param productId 조회할 상품 ID
     * @return 재고 정보 DTO
     * @author kimyongjun0129
     */
    @Transactional(readOnly = true)
    public AddStockResult getStock(Long productId) {
        Stock stock = stockRepository.findById(new ProductId(productId));
        return  AddStockResult.from(stock);
    }

    /**
     * 특정 상품의 재고를 삭제합니다.
     * <p>
     * 삭제 전, 재고 수량이 0인지 확인합니다.
     *
     * @param productId 삭제할 상품 ID
     * @author kimyongjun0129
     */
    @Transactional
    public void deleteStock(Long productId) {
        Stock stock = stockRepository.findById(new ProductId(productId));
        stock.checkDeletableOnlyIfStockEmpty();
        stockRepository.delete(new ProductId(productId));
    }
}