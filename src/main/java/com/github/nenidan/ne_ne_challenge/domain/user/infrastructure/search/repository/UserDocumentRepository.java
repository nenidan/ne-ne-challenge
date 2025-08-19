package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.search.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.search.document.UserDocument;

@Repository
public interface UserDocumentRepository extends ElasticsearchRepository<UserDocument, String> {
    List<UserDocument> findByEmailContainingOrNicknameContainingOrBioContaining(String email, String nickname, String bio);
}
