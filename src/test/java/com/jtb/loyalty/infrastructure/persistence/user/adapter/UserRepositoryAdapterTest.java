package com.jtb.loyalty.infrastructure.persistence.user.adapter;

import com.jtb.loyalty.domain.user.model.Role;
import com.jtb.loyalty.domain.user.model.User;
import com.jtb.loyalty.infrastructure.persistence.user.UserPanacheRepository;
import com.jtb.loyalty.infrastructure.persistence.user.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

    @Mock
    UserPanacheRepository panacheRepository;

    @InjectMocks
    UserRepositoryAdapter adapter;

    private UserEntity entity(UUID id, String username) {
        UserEntity entity = new UserEntity();
        entity.setId(id);
        entity.setUsername(username);
        entity.setDisplayName("Juan Perez");
        entity.setPasswordHash("hash");
        entity.setRole(Role.BARBER);
        entity.setActive(true);
        entity.setCreatedAt(Instant.parse("2026-01-01T00:00:00Z"));
        entity.setUpdatedAt(Instant.parse("2026-01-01T00:00:00Z"));
        return entity;
    }

    @Test
    @DisplayName("findById devuelve vacío cuando el id es nulo")
    void findById_devuelveVacio_cuandoElIdEsNulo() {
        assertThat(adapter.findById(null)).isEmpty();
    }

    @Test
    @DisplayName("findById mapea la entidad a usuario de dominio cuando existe")
    void findById_mapeaLaEntidadAUsuarioDeDominio_cuandoExiste() {
        UUID id = UUID.randomUUID();
        when(panacheRepository.findByIdOptional(id)).thenReturn(Optional.of(entity(id, "juan.perez")));

        Optional<User> result = adapter.findById(id);

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("juan.perez");
    }

    @Test
    @DisplayName("findByUsername devuelve vacío si el username es nulo o en blanco")
    void findByUsername_devuelveVacio_siElUsernameEsNuloOBlanco() {
        assertThat(adapter.findByUsername(null)).isEmpty();
        assertThat(adapter.findByUsername("   ")).isEmpty();
    }

    @Test
    @DisplayName("findByUsername normaliza a minúsculas antes de buscar")
    void findByUsername_normalizaAMinusculasAntesDeBuscar() {
        when(panacheRepository.findByUsername("juan.perez"))
                .thenReturn(Optional.of(entity(UUID.randomUUID(), "juan.perez")));

        Optional<User> result = adapter.findByUsername("  Juan.Perez  ");

        assertThat(result).isPresent();
        verify(panacheRepository).findByUsername("juan.perez");
    }

    @Test
    @DisplayName("existsByUsername devuelve false si el username es nulo o en blanco")
    void existsByUsername_devuelveFalse_siElUsernameEsNuloOBlanco() {
        assertThat(adapter.existsByUsername(null)).isFalse();
        assertThat(adapter.existsByUsername(" ")).isFalse();
        verify(panacheRepository, never()).existsByUsername(any());
    }

    @Test
    @DisplayName("existsByUsername normaliza y delega en el repositorio")
    void existsByUsername_normalizaYDelegaEnElRepositorio() {
        when(panacheRepository.existsByUsername("juan.perez")).thenReturn(true);

        assertThat(adapter.existsByUsername("  JUAN.PEREZ  ")).isTrue();
    }

    @Test
    @DisplayName("save actualiza la entidad existente")
    void save_actualizaLaEntidadExistente() {
        UUID id = UUID.randomUUID();
        UserEntity existing = entity(id, "juan.perez");
        when(panacheRepository.findByIdOptional(id)).thenReturn(Optional.of(existing));

        User toSave = User.builder()
                .id(id)
                .username("juan.perez")
                .displayName("Juan Perez Actualizado")
                .passwordHash("nuevo-hash")
                .role(Role.ADMIN)
                .active(false)
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2026-02-01T00:00:00Z"))
                .build();

        User saved = adapter.save(toSave);

        assertThat(existing.getDisplayName()).isEqualTo("Juan Perez Actualizado");
        assertThat(existing.getRole()).isEqualTo(Role.ADMIN);
        assertThat(saved.getDisplayName()).isEqualTo("Juan Perez Actualizado");
        verify(panacheRepository, never()).persist(any(UserEntity.class));
    }

    @Test
    @DisplayName("save crea una nueva entidad cuando no existe")
    void save_creaUnaNuevaEntidad_cuandoNoExiste() {
        UUID id = UUID.randomUUID();
        when(panacheRepository.findByIdOptional(id)).thenReturn(Optional.empty());

        User toSave = User.builder()
                .id(id)
                .username("nuevo.usuario")
                .displayName("Nuevo Usuario")
                .passwordHash("hash")
                .role(Role.BARBER)
                .active(true)
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .build();

        User saved = adapter.save(toSave);

        verify(panacheRepository).persist(any(UserEntity.class));
        assertThat(saved.getUsername()).isEqualTo("nuevo.usuario");
    }
}
