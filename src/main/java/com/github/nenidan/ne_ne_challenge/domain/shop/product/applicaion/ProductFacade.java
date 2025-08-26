package com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.CreateProductCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductStatisticsResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.UpdateProductCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.service.ProductCacheService;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.service.ProductService;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.model.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.event.StockDeleteEvent;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductService productService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ProductCacheService productCacheService;

    public ProductResult createProduct(CreateProductCommand createProductCommand) {
        ProductResult saveProduct = productService.createProduct(createProductCommand);

        // 상품 캐시 업데이트
        productCacheService.putMainPageCache();
        return saveProduct;
    }

    public ProductResult updateProduct(Long productId, UpdateProductCommand updateProductCommand) {
        ProductResult updateProduct = productService.updateProduct(productId, updateProductCommand);

        // 상품 캐시 업데이트
        productCacheService.putMainPageCache();

        return updateProduct;
    }

    public ProductResult findProduct(Long productId) {
        return productService.findProduct(productId);
    }

    public CursorResponse<ProductResult, Long> findAllProducts(
        List<Long> cursor,
        int size,
        String keyword
    ) {
        List<ProductResult> productResultList;

        // 첫번째 메인 페이지인 경우
        if (cursor == null && keyword == null) {
            // 캐시 기반 조회
            List<Product> productList = productCacheService.getMainPageWithFallback();

            productResultList = productList.stream()
                .map(ProductResult::fromEntity)
                .toList();

            return CursorResponse.of(productResultList, res -> res.getId().getValue(), size);
        } else {
            // 첫 페이지가 아닌 경우 -> DB 직접 조회
            return productService.findAllProducts(cursor,size+1, keyword);
        }
    }

    public void deleteProduct(Long productId) {
        productService.deleteProduct(productId);

        // 상품 캐시 업데이트
        productCacheService.putMainPageCache();

        // 상품이 삭제될 때, 상품과 관련된 재고와 리뷰도 삭제하기 위해 이벤트 발행
        applicationEventPublisher.publishEvent(new StockDeleteEvent(new ProductId(productId)));
    }

    public List<ProductStatisticsResult> getAllProducts() {
        return productService.getAllProducts();
    }
}
