package com.jtb.loyalty.domain.auth.repository;

import com.jtb.loyalty.domain.user.model.User;

import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de persistencia de usuarios.
 * El dominio no sabe si detrás hay PostgreSQL, JPA u otra cosa.
 */
public interface UserRepository {
    Optional<User> findById(UUID id);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    User save(User user);
}
