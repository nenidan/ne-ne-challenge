package com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.entity;

import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Document(indexName = "products")
@Setting(settingPath = "es-settings/product-settings.json")
@Mapping(mappingPath = "es-settings/product-mappings.json")
@AllArgsConstructor
public class ProductDocument {

    @Id
    private Long id;

    private String productName;

    private String productDescription;

    private Integer productPrice;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Instant createdAt;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Instant updatedAt;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Instant deletedAt;
}
