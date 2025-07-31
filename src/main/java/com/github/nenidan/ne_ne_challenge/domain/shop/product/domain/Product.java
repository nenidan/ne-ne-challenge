package com.github.nenidan.ne_ne_challenge.domain.shop.product.domain;

import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopException;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.vo.ProductId;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product {
    private ProductId productId;
    private String productName;
    private String productDescription;
    private int productPrice;
    private boolean deleted = false;

    public Product(ProductId productId, String productName, String productDescription, int productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }

    public Product(String productName, String productDescription, int productPrice) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }

    public static Product create(String productName, String productDescription, int productPrice) {
        return new Product(productName, productDescription, productPrice);
    }

    public void delete() {
        if (deleted) {
            throw new ShopException(ShopErrorCode.ORDER_ALREADY_CANCELED);
        }
        this.deleted = true;
    }
}
