package com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.service;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.model.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.repository.ProductRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.repository.ProductRepositoryImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProductCacheService {

    private final CacheManager cacheManager;
    private final ProductRepository productRepository;

    public ProductCacheService(
        CacheManager cacheManager,
        ProductRepository productRepository
    ) {
        this.cacheManager = cacheManager;
        this.productRepository = productRepository;
    }

    public static String PRODUCT_CACHE_NAME = "product_first_page";
    public static String PRODUCT_CACHE_KEY = "default";
    public static int PRODUCT_CACHE_SIZE = 30;

    // 애플리케이션 시작 시, 캐시 미리 적재 (Warm-up)
    @EventListener(ApplicationReadyEvent.class)
    public void warmUpCache() {
        List<Product> productList = productRepository.findAllByCursor(null, PRODUCT_CACHE_SIZE, null);
        Cache cache = cacheManager.getCache(PRODUCT_CACHE_NAME);
        if (cache != null) {
            cache.put(PRODUCT_CACHE_KEY, productList);
        } else {
            log.warn("캐시가 적재되지 않았습니다.");
        }
    }

    /**
     * 상품 첫 페이지 데이터를 캐시로 갱신합니다.
     * <p>
     * 이 메서드는 {@link ProductRepositoryImpl#findAllByCursor(Long, int, String)} 메서드를 호출하여,
     * 첫 페이지에 해당하는 상품 목록을 조회하고, 해당 결과를 캐시 이름 {@code PRODUCT_CACHE_NAME}의
     * {@code PRODUCT_CACHE_KEY}에 저장합니다.
     * <p>
     *
     */
    public void putMainPageCache() {
        List<Product> productList = productRepository.findAllByCursor(null, PRODUCT_CACHE_SIZE, null);
        Cache cache = cacheManager.getCache(PRODUCT_CACHE_NAME);
        if (cache != null) {
            cache.put(PRODUCT_CACHE_KEY, productList);
            log.info("캐시 갱신 완료");
        }
    }

    @Cacheable(condition = "#cursor == null && #keyword == null", cacheNames = "product_first_page", key = "'default'")
    public List<Product> getMainPageCache(Long cursor, int size, String keyword) {
        return productRepository.findAllByCursor(cursor, size, keyword);
    }
}
