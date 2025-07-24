package com.github.nenidan.ne_ne_challenge.domain.shop.entity;

import org.hibernate.annotations.SQLRestriction;

import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@SQLRestriction("deleted_at IS NULL")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String productName;

    @Setter
    @Column(nullable = false)
    private String productDescription;

    @Setter
    @Column(nullable = false)
    private Integer productPrice;

    public Product(String productName, String productDescription, Integer productPrice) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }
}