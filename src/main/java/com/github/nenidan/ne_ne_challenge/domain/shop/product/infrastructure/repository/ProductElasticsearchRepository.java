package com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.entity.ProductDocument;

public interface ProductElasticsearchRepository extends ElasticsearchRepository<ProductDocument, Long> {
}
