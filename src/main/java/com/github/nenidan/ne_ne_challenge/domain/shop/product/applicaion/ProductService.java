package com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.CreateProductCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.UpdateProductCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.ProductRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.event.ReviewDeleteEvent;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.event.StockRegisteredEvent;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, ApplicationEventPublisher applicationEventPublisher) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public ProductResult createProduct(CreateProductCommand createProductCommand) {
        Product product = Product.create(
            createProductCommand.getProductName(),
            createProductCommand.getProductDescription(),
            createProductCommand.getProductPrice()
        );
        Product saveProduct = productRepository.save(product);
        applicationEventPublisher.publishEvent(new StockRegisteredEvent(saveProduct.getProductId()));
        return ProductResult.fromEntity(saveProduct);
    }

    @Transactional
    public ProductResult updateProduct(Long productId, UpdateProductCommand updateProductCommand) {
        Product product = productRepository.findById(new ProductId(productId));
        productMapper.updateProductFromDto(updateProductCommand, product);
        Product updateProduct = productRepository.update(new ProductId(productId), product);
        return ProductResult.fromEntity(updateProduct);
    }

    @Transactional(readOnly = true)
    public ProductResult findProduct(Long productId) {
        Product product = productRepository.findById(new ProductId(productId));
        return ProductResult.fromEntity(product);
    }

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

        return new CursorResponse<>(content, nextCursor, productList.size() > size);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(new ProductId(productId));
        product.delete();
        productRepository.delete(new ProductId(productId));
        applicationEventPublisher.publishEvent(new ReviewDeleteEvent(new ProductId(productId)));
    }
}
