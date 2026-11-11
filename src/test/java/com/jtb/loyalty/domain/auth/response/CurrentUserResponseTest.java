package com.jtb.loyalty.domain.auth.response;

import com.jtb.loyalty.domain.user.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CurrentUserResponseTest {

    @Test
    @DisplayName("Expone los datos del usuario")
    void exponeLosDatosDelUsuario() {
        UUID id = UUID.randomUUID();
        CurrentUserResponse response = new CurrentUserResponse(id, "juan.perez", "Juan Perez", Role.ADMIN);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.username()).isEqualTo("juan.perez");
        assertThat(response.displayName()).isEqualTo("Juan Perez");
        assertThat(response.role()).isEqualTo(Role.ADMIN);
    }
}
