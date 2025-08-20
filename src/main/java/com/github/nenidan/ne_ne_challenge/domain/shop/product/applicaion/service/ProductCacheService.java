package com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.service;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.model.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.repository.ProductRepository;
import com.github.nenidan.ne_ne_challenge.global.util.CacheHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductCacheService {

    public static String PRODUCT_CACHE_NAME = "product_first_page";
    public static String PRODUCT_CACHE_KEY = "default";
    public static int PRODUCT_CACHE_SIZE = 30;
    private final CacheManager cacheManager;
    private final CacheHelper cacheHelper;
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    // 애플리케이션 시작 시, 캐시 미리 적재 (Warm-up)
    @EventListener(ApplicationReadyEvent.class)
    public void warmUpCache() {
        List<Product> productList = productRepository.findAllByKeyword(null, PRODUCT_CACHE_SIZE, null);
        Cache cache = cacheManager.getCache(PRODUCT_CACHE_NAME);
        if (cache != null) {
            cache.put(PRODUCT_CACHE_KEY, productList);
        } else {
            log.warn("캐시가 적재되지 않았습니다.");
        }
    }

    public void putMainPageCache() {
        List<Product> productList = productRepository.findAllByKeyword(null, PRODUCT_CACHE_SIZE, null);
        Cache cache = cacheManager.getCache(PRODUCT_CACHE_NAME);
        if (cache != null) {
            cache.put(PRODUCT_CACHE_KEY, productList);
            log.info("캐시 갱신 완료");
        }
    }

    public List<Product> getMainPageWithFallback() {
        Object orLoad = cacheHelper.getOrLoad(
            PRODUCT_CACHE_NAME,
            PRODUCT_CACHE_KEY,
            () -> {
                log.info("cache miss -> DB 에서 첫 페이지 상품 조회");
                return productRepository.findAllByKeyword(null, PRODUCT_CACHE_SIZE, null);
            }
        );

        return objectMapper.convertValue(orLoad, new TypeReference<>() {});
    }
}
