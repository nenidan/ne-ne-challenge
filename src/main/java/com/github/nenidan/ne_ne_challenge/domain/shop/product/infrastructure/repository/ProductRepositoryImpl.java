package com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.exception.ProductErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.exception.ProductException;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.model.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.repository.ProductRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.entity.ProductEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.mapper.ProductMapper;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductQueryDslRepository  productQueryDslRepository;
    private final ProductCacheService productCacheService;

    public ProductRepositoryImpl(
        ProductJpaRepository productJpaRepository,
        ProductCacheService productCacheService,
        ProductQueryDslRepository  productQueryDslRepository
    ) {
        this.productJpaRepository = productJpaRepository;
        this.productCacheService = productCacheService;
        this.productQueryDslRepository = productQueryDslRepository;
    }

    /**
     * 상품을 저장하고, 트랜잭션 커밋 이후 첫 페이지 캐시를 갱신합니다.
     * <p>
     * 1. {@code product}를 {@link ProductEntity}로 변환하여 DB에 저장한 뒤,
     * 2. 캐시 이름 {@code "first_page"}, 키 {@code "productList"}에 해당하는 캐시를 삭제하고,
     * 3. 트랜잭션 커밋 이후 {@link ProductCacheService#refreshFirstPageCache()}를 호출하여 새 데이터를 캐싱합니다.
     * <p>
     * 이 구조는 데이터 정합성을 보장하기 위해 캐시 갱신을 트랜잭션 커밋 이후로 지연시킵니다.
     *
     * @param product 저장할 상품 도메인 객체
     * @return 저장된 상품 도메인 객체
     */
    @Override
    @CacheEvict(cacheNames = "product_first_page", key = "'product_list'")
    public Product save(Product product) {
        ProductEntity productEntity = ProductMapper.toEntity(product);
        productJpaRepository.save(productEntity);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                productCacheService.refreshFirstPageCache();
            }
        });

        return ProductMapper.toDomain(productEntity);
    }

    @Override
    public Product findById(ProductId productId) {
        ProductEntity productEntity = productJpaRepository.findById(productId.getValue())
            .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));
        return ProductMapper.toDomain(productEntity);
    }

    /**
     * 커서 기반으로 상품 목록을 조회합니다.
     * <p>
     * 이 메서드는 조건에 따라 캐시를 적용하여 성능을 최적화합니다.
     * {@code cursor == null} 이고 {@code keyword == null}인 경우, 즉 첫 페이지 요청일 경우에만
     * 캐시 이름 {@code "first_page"}와 키 {@code "productList"}로 결과를 캐싱합니다.
     * 이후 동일한 조건의 요청이 들어오면 캐시에서 데이터를 조회합니다.
     * <p>
     * 첫 페이지 외의 요청은 캐시를 사용하지 않고 항상 DB를 조회합니다.
     *
     * @param cursor   조회 시작 커서 (마지막 상품 ID). {@code null}이면 첫 페이지로 간주됩니다.
     * @param limit     한 페이지에 조회할 상품 수
     * @param keyword  상품 이름 검색 키워드 (null 이면 필터링 없음)
     * @return         조건에 맞는 상품 목록
     */
    @Override
    @Cacheable(condition = "#cursor == null && #keyword == null", cacheNames = "product_first_page", key = "'product_list'")
    public List<Product> findAllByCursor(Long cursor, int limit, String keyword) {
        return productQueryDslRepository.findAllProductsBy(cursor, keyword, limit)
                    .stream()
                    .map(ProductMapper::toDomain)
                    .toList();
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll()
            .stream()
            .map(ProductMapper::toDomain)
            .toList();
    }
}
