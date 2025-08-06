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

    /**
     * 상품 첫 페이지 데이터를 캐시로 갱신합니다.
     * <p>
     * 이 메서드는 AOP 프록시 객체를 통해 {@link ProductRepositoryImpl#findAllByCursor(Long, int, String)} 메서드를 호출하여,
     * 첫 페이지에 해당하는 상품 목록을 조회하고, 해당 결과를 캐시 이름 {@code PRODUCT_CACHE_NAME}의
     * {@code PRODUCT_CACHE_KEY}에 저장합니다.
     * <p>
     * 주의: {@code findAllByCursor()}는 {@code @Cacheable} 어노테이션이 적용되어 있기 때문에,
     * 동일 클래스 내부에서 직접 호출하면 캐싱이 적용되지 않습니다.
     * 따라서 Spring AOP 프록시 객체를 통해 호출해야 합니다.
     *
     */
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
