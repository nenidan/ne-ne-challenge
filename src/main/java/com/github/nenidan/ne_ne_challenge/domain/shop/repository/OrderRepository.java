package com.github.nenidan.ne_ne_challenge.domain.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
