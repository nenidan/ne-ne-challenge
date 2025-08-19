package com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.repository;


import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.exception.ProductErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.exception.ProductException;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.model.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.repository.ProductRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.entity.ProductDocument;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.entity.ProductEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.mapper.ProductMapper;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductElasticsearchRepository productElasticsearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public Product save(Product product) {
        // domain model -> infra entity 변환
        ProductEntity productEntity = ProductMapper.toEntity(product);
        // DB에 저장
        ProductEntity saveProductEntity = productJpaRepository.save(productEntity);
        // infra entity -> infra document 변환
        ProductDocument productDocument = ProductMapper.fromEntity(saveProductEntity);
        // Elasticsearch 색인 저장
        productElasticsearchRepository.save(productDocument);
        return ProductMapper.toDomain(productEntity);
    }

    @Override
    public Product findByIdFromElasticsearch(ProductId productId) {
        ProductDocument productDocument = productElasticsearchRepository.findById(productId.getValue())
            .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));
        return ProductMapper.fromDocument(productDocument);
    }

    @Override
    public Product findByIdFromJpa(ProductId productId) {
        ProductEntity productEntity = productJpaRepository.findById(productId.getValue())
            .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));
        return ProductMapper.toDomain(productEntity);
    }

    @Override
    public List<Product> findAllByKeyword(List<Object> after, int size, String keyword) {
        Query query =
            Query.of(q -> q.bool(b -> {
                b.mustNot(f -> f.exists(e -> e.field("deletedAt")));

                if (keyword == null || keyword.isBlank()) {
                    b.must(m -> m.matchAll(ma -> ma));
                } else {
                    b.must(m -> m.match(mm -> mm.field("productName").query(keyword)));
                }
                return b;
            }));

        NativeQuery nativeQuery = NativeQuery.builder()
            .withQuery(query)

            .withSort(s -> s.field(f -> f.field("id").order(SortOrder.Desc)))

            .withPageable(PageRequest.of(0, size))
            .withSearchAfter(after)
            .withTrackTotalHits(false)
            .build();

        // 실행
        SearchHits<ProductDocument> search = elasticsearchOperations.search(nativeQuery, ProductDocument.class);

        // 매핑
        List<ProductDocument> list = search.getSearchHits().stream()
            .map(SearchHit::getContent)
            .toList();

        return list.stream()
            .map(ProductMapper::fromDocument)
            .toList();
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll()
            .stream()
            .map(ProductMapper::toDomain)
            .toList();
    }
}
