package com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.model;

import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.exception.ProductErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.exception.ProductException;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.Getter;

@Getter
public class Product {
    private final ProductId productId;
    private String productName;
    private String productDescription;
    private Integer productPrice;
    private LocalDateTime deletedAt;

    private Product(ProductId productId, String productName, String productDescription, Integer productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }

    public static Product create(ProductId productId, String productName, String productDescription, Integer productPrice) {
        return new Product(productId, productName, productDescription, productPrice);
    }

    public void update(String productName, String productDescription, Integer productPrice) {
        if (productName != null && !productName.isBlank()) {
            this.productName = productName;
        }

        if(productDescription != null && !productDescription.isBlank()){
            this.productDescription = productDescription;
        }

        if(productPrice != null) {
            this.productPrice = productPrice;
        }
    }

    public void delete() {
        if (deletedAt != null) {
            throw new ProductException(ProductErrorCode.PRODUCT_ALREADY_DELETED);
        }
        this.deletedAt = LocalDateTime.now();
    }
}
