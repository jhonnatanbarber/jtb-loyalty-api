package com.jtb.loyalty.infrastructure.persistence.user.adapter;

import com.jtb.loyalty.domain.auth.repository.UserRepository;
import com.jtb.loyalty.domain.user.model.User;
import com.jtb.loyalty.infrastructure.persistence.user.UserPanacheRepository;
import com.jtb.loyalty.infrastructure.persistence.user.entity.UserEntity;
import com.jtb.loyalty.infrastructure.persistence.user.mapper.UserPersistenceMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserRepositoryAdapter implements UserRepository {

    @Inject
    UserPanacheRepository panacheRepository;

    @Override
    public Optional<User> findById(UUID id) {
        if (id == null) {
            return Optional.empty();
        }
        return panacheRepository.findByIdOptional(id)
                .map(UserPersistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        if (username == null || username.isBlank()) {
            return Optional.empty();
        }
        return panacheRepository.findByUsername(normalize(username))
                .map(UserPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        if (username == null || username.isBlank()) {
            return false;
        }
        return panacheRepository.existsByUsername(normalize(username));
    }

    @Override
    @Transactional
    public User save(User user) {
        UserEntity entity = panacheRepository.findByIdOptional(user.getId())
                .map(existing -> {
                    UserPersistenceMapper.copyMutableFields(user, existing);
                    return existing;
                })
                .orElseGet(() -> {
                    UserEntity created = UserPersistenceMapper.toNewEntity(user);
                    panacheRepository.persist(created);
                    return created;
                });
        return UserPersistenceMapper.toDomain(entity);
    }

    private static String normalize(String username) {
        return username.strip().toLowerCase(Locale.ROOT);
    }
}