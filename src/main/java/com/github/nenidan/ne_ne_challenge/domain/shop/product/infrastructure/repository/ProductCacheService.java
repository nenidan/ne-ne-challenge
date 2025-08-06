package com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.model.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProductCacheService {

    private final CacheManager cacheManager;
    private final ApplicationContext applicationContext;

    public ProductCacheService(
        @Qualifier("caffeineCacheManager") CacheManager cacheManager,
        ApplicationContext applicationContext
    ) {
        this.cacheManager = cacheManager;
        this.applicationContext = applicationContext;
    }

    public static String PRODUCT_CACHE_NAME = "product_first_page";
    public static String PRODUCT_CACHE_KEY = "product_list";
    public static int PRODUCT_CACHE_SIZE = 30;

    public void refreshFirstPageCache() {
        ProductRepositoryImpl proxy = applicationContext.getBean(ProductRepositoryImpl.class);
        List<Product> productList = proxy.findAllByCursor(null, PRODUCT_CACHE_SIZE, null);
        Cache cache = cacheManager.getCache(PRODUCT_CACHE_NAME);
        if (cache != null) {
            cache.put(PRODUCT_CACHE_KEY, productList);
            log.info("캐시 갱신 완료");
        }
    }
}
