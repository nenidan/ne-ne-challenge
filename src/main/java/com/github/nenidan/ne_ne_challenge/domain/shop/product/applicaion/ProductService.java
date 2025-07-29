package com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.CreateProductRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.UpdateProductRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.ProductRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ReviewDeleteEvent;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.StockRegisteredEvent;
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
    public ProductResponse createProduct(CreateProductRequest createProductRequest) {
        Product product = Product.create(
            createProductRequest.getProductName(),
            createProductRequest.getProductDescription(),
            createProductRequest.getProductPrice()
        );
        Product saveProduct = productRepository.save(product);
        applicationEventPublisher.publishEvent(new StockRegisteredEvent(saveProduct.getProductId()));
        return ProductResponse.fromEntity(saveProduct);
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, UpdateProductRequest updateProductRequest) {
        Product product = productRepository.findById(new ProductId(productId));
        productMapper.updateProductFromDto(updateProductRequest, product);
        Product updateProduct = productRepository.update(new ProductId(productId), product);
        return ProductResponse.fromEntity(updateProduct);
    }

    @Transactional(readOnly = true)
    public ProductResponse findProduct(Long productId) {
        Product product = productRepository.findById(new ProductId(productId));
        return ProductResponse.fromEntity(product);
    }

    @Transactional(readOnly = true)
    public CursorResponse<ProductResponse, Long> findAllProducts(
        Long cursor,
        int size,
        String keyword
    ) {
        List<ProductResponse> productList = productRepository.findAllByCursor(cursor,size+1, keyword)
            .stream()
            .map(ProductResponse::fromEntity)
            .toList();

        boolean hasNext = productList.size() > size;

        List<ProductResponse> content = hasNext ? productList.subList(0, size) : productList;

        Long nextCursor = hasNext ? productList.get(productList.size() - 1).getId() : null;

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
