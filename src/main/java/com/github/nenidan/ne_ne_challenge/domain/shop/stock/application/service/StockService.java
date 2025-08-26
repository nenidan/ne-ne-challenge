package com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto.AddStockCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto.AddStockResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.model.Stock;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.repository.StockRepository;
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
    public AddStockResult inBoundStock(ProductId productId, AddStockCommand addStockCommand) {
        Stock stock = stockRepository.findById(productId);
        stock.inbound(addStockCommand.getQuantity());
        stockRepository.save(stock);
        return AddStockResult.from(stock);
    }

    /**
     * 재고를 감소시킵니다.
     *
     * @param addStockCommand 재고 감소 명령(상품 ID, 감소 수량)
     * @author kimyongjun0129
     */
    @Transactional
    public void decreaseStock(ProductId productId, AddStockCommand addStockCommand) {
        Stock stock = stockRepository.findById(productId);
        stock.decreaseQuantity(addStockCommand.getQuantity());
        stockRepository.save(stock);
    }

    /**
     * 예비 재고를 감소시킵니다.
     *
     * @param addStockCommand 예비 재고 감소 명령(상품 ID, 감소 수량)
     * @author kimyongjun0129
     */
    @Transactional
    public void decreaseReservedStock(ProductId productId, AddStockCommand addStockCommand) {
        Stock stock = stockRepository.findById(productId);
        stock.decreaseReservedQuantity(addStockCommand.getQuantity());
        stockRepository.save(stock);
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
        return AddStockResult.from(stock);
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
        stock.delete();
        stockRepository.save(stock);
    }

    /**
     * 주문 취소 시, 재고를 복구합니다.
     * <p>
     * 복구하기 전, 복구 수량이 0이하인지 확인합니다.
     *
     * @param addStockCommand 재고 복구 명령(상품 ID, 복구 수량)
     * @author kimyongjun0129
     */
    @Transactional
    public void restoreStock(ProductId productId, AddStockCommand addStockCommand) {
        Stock stock = stockRepository.findById(productId);
        stock.restoreQuantity(addStockCommand.getQuantity());
        stockRepository.save(stock);
    }

    /**
     * 주문 취소 시, 예비 재고를 복구합니다.
     * <p>
     * 복구하기 전, 복구 수량이 0이하인지 확인합니다.
     *
     * @param addStockCommand 재고 복구 명령(상품 ID, 복구 수량)
     * @author kimyongjun0129
     */
    @Transactional
    public void restoreReservedStock(ProductId productId, AddStockCommand addStockCommand) {
        Stock stock = stockRepository.findById(productId);
        stock.restoreReservedQuantity(addStockCommand.getQuantity());
        stockRepository.save(stock);
    }

    /**
     * 예비 재고 복구를 취소합니다.
     * <p>
     * 복구하기 전, 복구 수량이 0이하인지 확인합니다.
     *
     * @param addStockCommand 재고 복구 명령(상품 ID, 복구 수량)
     * @author kimyongjun0129
     */
    @Transactional
    public void canceledRestoreReservedStock(ProductId productId, AddStockCommand addStockCommand) {
        Stock stock = stockRepository.findById(productId);
        stock.canceledRestoreReservedQuantity(addStockCommand.getQuantity());
        stockRepository.save(stock);
    }
}