package com.github.nenidan.ne_ne_challenge.domain.product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.entity.ProductEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.repository.ProductJpaRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(com.github.nenidan.ne_ne_challenge.global.config.PersistenceConfig.class)
public class ProductBulkInsertTest {

    @Autowired private ProductJpaRepository productJpaRepository;

    @Disabled
    @Test
    @Rollback(false)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void productInsertTest(){

        int batchSize = 100_000;
        int total = 1_000_000;
        List<ProductEntity> productEntities = new ArrayList<>();
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
            ProductEntity productEntity = new ProductEntity(
                null,
                i + productList.get(index),
                i + "내 상품과 교환 가능합니다.",
                i,
                ((i % 100_000 == 0) || (i % 100_000 > 90_000)) ? LocalDateTime.now() : null
            );
            productEntities.add(productEntity);

            if (i % batchSize == 0) {
                index++;
                productJpaRepository.saveAll(productEntities);
                productEntities.clear();
            }
        }
    }
}

