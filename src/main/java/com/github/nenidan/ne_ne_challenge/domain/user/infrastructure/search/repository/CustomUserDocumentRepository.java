package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.search.repository;


import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.search.document.UserDocument;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomUserDocumentRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    public List<UserDocument> findByKeywordWithCursor(String keyword, String cursor, int limit) {

        BoolQuery.Builder boolBuilder = new BoolQuery.Builder();

        // 키워드 조건
        if (keyword != null && !keyword.isEmpty()) {
            boolBuilder
                    .should(QueryBuilders.match(m -> m.field("email.ngram").query(keyword)))
                    .should(QueryBuilders.match(m -> m.field("nickname.ngram").query(keyword)))
                    .should(QueryBuilders.match(m -> m.field("bio.ngram").query(keyword)))
                    .minimumShouldMatch("1");
        } else {
            boolBuilder.must(QueryBuilders.matchAll().build()._toQuery());
        }

        // cursor 조건
        if (cursor != null && !cursor.isEmpty()) {
            boolBuilder.filter(f -> f.range(RangeQuery.of(r -> r
                    .term(t -> t
                            .field("nickname.keyword")
                            .gte(cursor)
                    )
            )));
        }

        // 정렬
        List<SortOptions> sortOptions = List.of(
                SortOptions.of(s -> s.field(FieldSort.of(f -> f.field("nickname.keyword").order(SortOrder.Asc))))
        );

        NativeQueryBuilder builder = NativeQuery.builder()
                .withQuery(Query.of(q -> q.bool(boolBuilder.build())))
                .withSort(sortOptions)
                .withPageable(PageRequest.of(0, limit));

        NativeQuery nativeQuery = builder.build();
        SearchHits<UserDocument> searchHits = elasticsearchOperations.search(nativeQuery, UserDocument.class);

        return searchHits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList();
    }
}
