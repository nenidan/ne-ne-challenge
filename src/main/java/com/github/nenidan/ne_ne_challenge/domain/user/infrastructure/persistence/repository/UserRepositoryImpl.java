package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.User;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.repository.UserRepository;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.AccountEntity;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.ProfileEntity;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaAccountRepository jpaAccountRepository;
    private final JpaProfileRepository jpaProfileRepository;
    private final QProfieRepository qProfieRepository;


    @Override
    public User save(User user) {
        ProfileEntity profileEntity = UserMapper.toEntity(user);
        AccountEntity accountEntity = profileEntity.getAccount();

        AccountEntity savedAccount = jpaAccountRepository.save(accountEntity);

        profileEntity.setAccount(savedAccount);

        ProfileEntity savedProfile = jpaProfileRepository.save(profileEntity);

        return UserMapper.toDomain(savedProfile);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaAccountRepository.countByEmail(email) != 0L;
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return jpaProfileRepository.existsByNickname(nickname);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaProfileRepository.findByAccountEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaProfileRepository.findById(id)
                .map(UserMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaProfileRepository.findAll()
                .stream()
                .map(UserMapper::toDomain)
                .toList();
    }

    @Override
    public List<User> findByKeyword(String cursor, String keyword, int limit) {
        return qProfieRepository.findByKeyword(cursor, keyword, limit)
                .stream()
                .map(UserMapper::toDomain)
                .toList();
    }

    @Override
    public User update(User user) {
        ProfileEntity profileEntity = UserMapper.toEntity(user);
        ProfileEntity savedEntity = jpaProfileRepository.save(profileEntity);
        jpaProfileRepository.flush();
        return UserMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findByKakaoId(String kakaoId) {
        return jpaProfileRepository.findByAccount_KakaoId(kakaoId)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByNaverId(String naverId) {
        return jpaProfileRepository.findByAccount_NaverId(naverId)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByGoogleId(String googleId) {
        return jpaProfileRepository.findByAccount_GoogleId(googleId)
                .map(UserMapper::toDomain);
    }

    @Override
    public void delete(User user) {
        ProfileEntity profileEntity = UserMapper.toEntity(user);

        jpaProfileRepository.save(profileEntity);
    }
}
