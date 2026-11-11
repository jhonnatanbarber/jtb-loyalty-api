package com.jtb.loyalty.infrastructure.persistence.user.mapper;

import com.jtb.loyalty.domain.user.model.Role;
import com.jtb.loyalty.domain.user.model.User;
import com.jtb.loyalty.infrastructure.persistence.user.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserPersistenceMapperTest {

    private UserEntity entity(UUID id) {
        UserEntity entity = new UserEntity();
        entity.setId(id);
        entity.setUsername("juan.perez");
        entity.setDisplayName("Juan Perez");
        entity.setPasswordHash("hash");
        entity.setRole(Role.ADMIN);
        entity.setActive(true);
        entity.setCreatedAt(Instant.parse("2026-01-01T00:00:00Z"));
        entity.setUpdatedAt(Instant.parse("2026-01-02T00:00:00Z"));
        return entity;
    }

    @Test
    @DisplayName("toDomain copia todos los campos de la entidad")
    void toDomain_copiaTodosLosCamposDeLaEntidad() {
        UUID id = UUID.randomUUID();
        UserEntity entity = entity(id);

        User user = UserPersistenceMapper.toDomain(entity);

        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getUsername()).isEqualTo("juan.perez");
        assertThat(user.getDisplayName()).isEqualTo("Juan Perez");
        assertThat(user.getPasswordHash()).isEqualTo("hash");
        assertThat(user.getRole()).isEqualTo(Role.ADMIN);
        assertThat(user.isActive()).isTrue();
        assertThat(user.getCreatedAt()).isEqualTo(entity.getCreatedAt());
        assertThat(user.getUpdatedAt()).isEqualTo(entity.getUpdatedAt());
    }

    @Test
    @DisplayName("toNewEntity copia id, createdAt y campos mutables")
    void toNewEntity_copiaIdCreatedAtYCamposMutables() {
        UUID id = UUID.randomUUID();
        User user = UserPersistenceMapper.toDomain(entity(id));

        UserEntity newEntity = UserPersistenceMapper.toNewEntity(user);

        assertThat(newEntity.getId()).isEqualTo(id);
        assertThat(newEntity.getCreatedAt()).isEqualTo(user.getCreatedAt());
        assertThat(newEntity.getUsername()).isEqualTo(user.getUsername());
        assertThat(newEntity.getDisplayName()).isEqualTo(user.getDisplayName());
        assertThat(newEntity.getPasswordHash()).isEqualTo(user.getPasswordHash());
        assertThat(newEntity.getRole()).isEqualTo(user.getRole());
        assertThat(newEntity.isActive()).isEqualTo(user.isActive());
        assertThat(newEntity.getUpdatedAt()).isEqualTo(user.getUpdatedAt());
    }

    @Test
    @DisplayName("copyMutableFields sobrescribe la entidad sin tocar id ni createdAt")
    void copyMutableFields_sobrescribeLaEntidadSinTocarIdNiCreatedAt() {
        UUID id = UUID.randomUUID();
        UserEntity target = entity(id);
        Instant originalCreatedAt = target.getCreatedAt();

        User user = User.builder()
                .id(id)
                .username("otro.usuario")
                .displayName("Otro Usuario")
                .passwordHash("otro-hash")
                .role(Role.BARBER)
                .active(false)
                .createdAt(Instant.parse("2020-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2026-03-01T00:00:00Z"))
                .build();

        UserPersistenceMapper.copyMutableFields(user, target);

        assertThat(target.getId()).isEqualTo(id);
        assertThat(target.getCreatedAt()).isEqualTo(originalCreatedAt);
        assertThat(target.getUsername()).isEqualTo("otro.usuario");
        assertThat(target.getDisplayName()).isEqualTo("Otro Usuario");
        assertThat(target.getPasswordHash()).isEqualTo("otro-hash");
        assertThat(target.getRole()).isEqualTo(Role.BARBER);
        assertThat(target.isActive()).isFalse();
        assertThat(target.getUpdatedAt()).isEqualTo(Instant.parse("2026-03-01T00:00:00Z"));
    }
}
