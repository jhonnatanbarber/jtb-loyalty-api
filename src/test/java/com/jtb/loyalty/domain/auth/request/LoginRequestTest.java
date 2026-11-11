package com.jtb.loyalty.domain.auth.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class LoginRequestTest {

    @Test
    @DisplayName("Expone usuario y contraseña sin modificarlos")
    void exponeUsuarioYContrasenaSinModificarlos() {
        LoginRequest request = new LoginRequest("juan.perez", "secret");

        assertThat(request.username()).isEqualTo("juan.perez");
        assertThat(request.password()).isEqualTo("secret");
    }
}
