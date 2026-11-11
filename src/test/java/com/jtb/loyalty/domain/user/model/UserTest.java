package com.jtb.loyalty.domain.user.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserTest {

    private User newUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .username("juan.perez")
                .displayName("Juan Perez")
                .passwordHash("hash-inicial")
                .role(Role.BARBER)
                .active(true)
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .build();
    }

    @Test
    @DisplayName("changePassword actualiza el hash y la fecha de actualización")
    void changePassword_actualizaElHashYLaFechaDeActualizacion() {
        User user = newUser();
        Instant now = Instant.parse("2026-02-01T00:00:00Z");

        user.changePassword("hash-nuevo", now);

        assertThat(user.getPasswordHash()).isEqualTo("hash-nuevo");
        assertThat(user.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("rename normaliza y actualiza el nombre visible")
    void rename_normalizaYActualizaElNombreVisible() {
        User user = newUser();
        Instant now = Instant.parse("2026-02-01T00:00:00Z");

        user.rename("  Juan Carlos  ", now);

        assertThat(user.getDisplayName()).isEqualTo("Juan Carlos");
        assertThat(user.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("changeRole actualiza el rol")
    void changeRole_actualizaElRol() {
        User user = newUser();
        Instant now = Instant.parse("2026-02-01T00:00:00Z");

        user.changeRole(Role.ADMIN, now);

        assertThat(user.getRole()).isEqualTo(Role.ADMIN);
        assertThat(user.isAdmin()).isTrue();
    }

    @Test
    @DisplayName("changeRole rechaza rol nulo")
    void changeRole_rechazaRolNulo() {
        User user = newUser();

        assertThatThrownBy(() -> user.changeRole(null, Instant.now()))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("deactivate y activate cambian el estado activo")
    void deactivateYActivate_cambianElEstadoActivo() {
        User user = newUser();
        Instant now = Instant.parse("2026-02-01T00:00:00Z");

        user.deactivate(now);
        assertThat(user.isActive()).isFalse();

        user.activate(now);
        assertThat(user.isActive()).isTrue();
    }

    @Test
    @DisplayName("isAdmin es falso para BARBER")
    void isAdmin_esFalsoParaBarber() {
        assertThat(newUser().isAdmin()).isFalse();
    }
}
