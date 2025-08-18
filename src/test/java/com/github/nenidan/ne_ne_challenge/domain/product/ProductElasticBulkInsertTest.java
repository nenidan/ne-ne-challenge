package com.github.nenidan.ne_ne_challenge.domain.product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest;
import org.springframework.test.context.TestPropertySource;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.entity.ProductDocument;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.repository.ProductElasticsearchRepository;



@DataElasticsearchTest
@TestPropertySource(properties = {
    "spring.elasticsearch.uris=http://localhost:9200",
    "spring.elasticsearch.username=elastic",
    "spring.elasticsearch.password=123456"
})
public class ProductElasticBulkInsertTest {

    @Autowired ProductElasticsearchRepository productElasticsearchRepository;

    @Disabled
    @Test
    void bulkInsertChunked() {
        int batchSize = 100_000;
        int total = 1_000_000;
        List<ProductDocument> productDocuments = new ArrayList<>();
        List<String> productList = new ArrayList<>(
            Arrays.asList(
                " GS25 기프티콘",
                " CU 기프티콘",
                " Kakao 기프티콘",
                " Google 기프티콘",
                " Naver 기프티콘",
                " Meta 기프티콘",
                " Tesla 기프티콘",
                " Roblox 기프티콘",
                " Kia 기프티콘",
                " banana 기프티콘"
            )
        );
        int index = 0;

        for (int i = 1; i <= total; i++) {
            LocalDateTime now = LocalDateTime.now();

            ProductDocument productDocument = new ProductDocument(
                (long) i,
                i + productList.get(index),
                i + "내 상품과 교환 가능합니다.",
                i,
                now,
                now,
                ((i % 100_000 == 0) || (i % 100_000 > 90_000)) ? LocalDateTime.now() : null
            );
            productDocuments.add(productDocument);

            if (i % batchSize == 0) {
                index++;
                productElasticsearchRepository.saveAll(productDocuments);
                productDocuments.clear();
            }
        }
    }
}