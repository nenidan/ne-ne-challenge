package com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.CreateProductCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.UpdateProductCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.model.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.repository.ProductRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.event.ReviewDeleteEvent;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.event.StockDeleteEvent;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.event.StockRegisteredEvent;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ProductService(ProductRepository productRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.productRepository = productRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 상품을 생성하고 재고 등록 이벤트를 발행합니다.
     *
     * @param createProductCommand 상품 생성 요청 DTO
     * @return 생성된 상품 결과 DTO
     * @author kimyongjun0129
     */
    @Transactional
    public ProductResult createProduct(CreateProductCommand createProductCommand) {
        Product product = Product.create(
            createProductCommand.getProductName(),
            createProductCommand.getProductDescription(),
            createProductCommand.getProductPrice()
        );
        Product saveProduct = productRepository.save(product);

        // 재고 등록 이벤트 발행
        applicationEventPublisher.publishEvent(new StockRegisteredEvent(saveProduct.getProductId()));
        return ProductResult.fromEntity(saveProduct);
    }


    /**
     * 상품 정보를 수정합니다.
     *
     * @param productId            수정할 상품 ID
     * @param updateProductCommand 수정할 상품 정보 DTO
     * @return 수정된 상품 결과 DTO
     * @author kimyongjun0129
     */
    @Transactional
    public ProductResult updateProduct(Long productId, UpdateProductCommand updateProductCommand) {
        Product product = productRepository.findById(new ProductId(productId));
        product.update(
            updateProductCommand.getProductName(),
            updateProductCommand.getProductDescription(),
            updateProductCommand.getProductPrice()
        );
        Product updateProduct = productRepository.save(product);
        return ProductResult.fromEntity(updateProduct);
    }

    /**
     * 단일 상품을 조회합니다.
     *
     * @param productId 조회할 상품 ID
     * @return 조회된 상품 결과 DTO
     * @author kimyongjun0129
     */
    @Transactional(readOnly = true)
    public ProductResult findProduct(Long productId) {
        Product product = productRepository.findById(new ProductId(productId));
        return ProductResult.fromEntity(product);
    }

    /**
     * 상품 목록을 커서 기반으로 조회합니다.
     *
     * @param cursor  현재 커서 (마지막으로 조회한 상품 ID)
     * @param size    조회할 데이터 개수
     * @param keyword 검색 키워드
     * @return 커서 응답 (조회된 상품 리스트, 다음 커서 값, 다음 페이지 존재 여부)
     * @author kimyongjun0129
     */
    @Transactional(readOnly = true)
    public CursorResponse<ProductResult, Long> findAllProducts(
        Long cursor,
        int size,
        String keyword
    ) {
        List<ProductResult> productList = productRepository.findAllByCursor(cursor,size+1, keyword)
            .stream()
            .map(ProductResult::fromEntity)
            .toList();

        boolean hasNext = productList.size() > size;

        List<ProductResult> content = hasNext ? productList.subList(0, size) : productList;

        Long nextCursor = hasNext ? productList.get(productList.size() - 1).getId().getValue() : null;

        return CursorResponse.of(content, nextCursor, productList.size() > size);
    }

    /**
     * 상품을 삭제하고, 재고 및 리뷰 삭제 이벤트를 발행합니다.
     *
     * @param productId 삭제할 상품 ID
     * @author kimyongjun0129
     */
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(new ProductId(productId));
        product.delete();
        productRepository.save(product);

        // 상품이 삭제될 때, 상품과 관련된 재고와 리뷰도 삭제하기 위해 이벤트 발행
        applicationEventPublisher.publishEvent(new StockDeleteEvent(new ProductId(productId)));
        applicationEventPublisher.publishEvent(new ReviewDeleteEvent(new ProductId(productId)));
    }
}
