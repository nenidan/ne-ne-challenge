package com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.mapper;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto.AddStockCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto.AddStockResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.event.StockUpdateEvent;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.dto.AddStockRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.dto.AddStockResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

public class AddStockMapper {
    public static AddStockCommand toAddStockCommand(Long productId, AddStockRequest addStockRequest){
        return new AddStockCommand(new ProductId(productId), addStockRequest.getQuantity());
    }

    public static AddStockResponse toAddStockResponse(AddStockResult addStockResult){
        return new AddStockResponse(
            addStockResult.getStockId(),
            addStockResult.getProductId(),
            addStockResult.getQuantity()
        );
    }

    public static AddStockCommand fromStockUpdateEvent(StockUpdateEvent stockUpdateEvent){
        return new  AddStockCommand(stockUpdateEvent.getProductId(), stockUpdateEvent.getQuantity());
    }
}
