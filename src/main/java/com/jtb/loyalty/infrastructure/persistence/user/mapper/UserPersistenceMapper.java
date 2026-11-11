package com.jtb.loyalty.infrastructure.persistence.user.mapper;

import com.jtb.loyalty.domain.user.model.User;
import com.jtb.loyalty.infrastructure.persistence.user.entity.UserEntity;

public final class UserPersistenceMapper {

    private UserPersistenceMapper() {
    }

    public static User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .displayName(entity.getDisplayName())
                .passwordHash(entity.getPasswordHash())
                .role(entity.getRole())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static UserEntity toNewEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setCreatedAt(user.getCreatedAt());
        copyMutableFields(user, entity);
        return entity;
    }

    public static void copyMutableFields(User user, UserEntity entity) {
        entity.setUsername(user.getUsername());
        entity.setDisplayName(user.getDisplayName());
        entity.setPasswordHash(user.getPasswordHash());
        entity.setRole(user.getRole());
        entity.setActive(user.isActive());
        entity.setUpdatedAt(user.getUpdatedAt());
    }
}