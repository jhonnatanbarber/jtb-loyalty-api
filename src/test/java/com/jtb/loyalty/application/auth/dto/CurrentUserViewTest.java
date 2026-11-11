package com.jtb.loyalty.application.auth.dto;

import com.jtb.loyalty.domain.user.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CurrentUserViewTest {

    @Test
    @DisplayName("Expone los datos del usuario autenticado")
    void exponeLosDatosDelUsuarioAutenticado() {
        UUID id = UUID.randomUUID();
        CurrentUserView view = new CurrentUserView(id, "juan.perez", "Juan Perez", Role.ADMIN);

        assertThat(view.id()).isEqualTo(id);
        assertThat(view.username()).isEqualTo("juan.perez");
        assertThat(view.displayName()).isEqualTo("Juan Perez");
        assertThat(view.role()).isEqualTo(Role.ADMIN);
    }
}
