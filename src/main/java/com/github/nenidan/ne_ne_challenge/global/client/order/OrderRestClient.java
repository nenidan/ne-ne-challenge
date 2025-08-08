package com.github.nenidan.ne_ne_challenge.global.client.order;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.global.client.order.dto.OrderStatisticsResponse;

public interface OrderRestClient {
    List<OrderStatisticsResponse> getAllOrders();
}
