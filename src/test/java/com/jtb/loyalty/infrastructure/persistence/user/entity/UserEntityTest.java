package com.jtb.loyalty.infrastructure.persistence.user.entity;

import com.jtb.loyalty.domain.user.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserEntityTest {

    @Test
    @DisplayName("Los setters y getters exponen los mismos valores asignados")
    void losSettersYGettersExponenLosMismosValoresAsignados() {
        UserEntity entity = new UserEntity();
        UUID id = UUID.randomUUID();
        Instant now = Instant.parse("2026-01-01T00:00:00Z");

        entity.setId(id);
        entity.setUsername("juan.perez");
        entity.setDisplayName("Juan Perez");
        entity.setPasswordHash("hash");
        entity.setRole(Role.ADMIN);
        entity.setActive(true);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getUsername()).isEqualTo("juan.perez");
        assertThat(entity.getDisplayName()).isEqualTo("Juan Perez");
        assertThat(entity.getPasswordHash()).isEqualTo("hash");
        assertThat(entity.getRole()).isEqualTo(Role.ADMIN);
        assertThat(entity.isActive()).isTrue();
        assertThat(entity.getCreatedAt()).isEqualTo(now);
        assertThat(entity.getUpdatedAt()).isEqualTo(now);
    }
}
