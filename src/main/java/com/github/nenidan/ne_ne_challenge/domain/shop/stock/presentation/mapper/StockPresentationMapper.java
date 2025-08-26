package com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.mapper;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto.AddStockCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto.AddStockResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.event.StockUpdateEvent;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.dto.AddStockRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.dto.AddStockResponse;

public class StockPresentationMapper {
    public static AddStockCommand toAddStockCommand(AddStockRequest addStockRequest){
        return new AddStockCommand(addStockRequest.getQuantity());
    }

    public static AddStockResponse toAddStockResponse(AddStockResult addStockResult){
        return new AddStockResponse(
            addStockResult.getStockId(),
            addStockResult.getProductId(),
            addStockResult.getQuantity()
        );
    }

    public static AddStockCommand fromStockUpdateEvent(StockUpdateEvent stockUpdateEvent){
        return new  AddStockCommand(stockUpdateEvent.getQuantity());
    }
}
