package com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.service.StockService;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.event.StockDeleteEvent;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.event.StockRegisteredEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockEventHandler {

    private final StockService stockService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void stockCreateHandler(StockRegisteredEvent stockRegisteredEvent) {
        stockService.createStock(stockRegisteredEvent.getProductId().getValue());
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void stockDeleteHandler(StockDeleteEvent stockDeleteEvent) {
        stockService.deleteStock(stockDeleteEvent.getProductId().getValue());
    }
}
