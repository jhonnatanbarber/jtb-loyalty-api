package com.jtb.loyalty.application.auth.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class LoginCommandTest {

    @Test
    @DisplayName("Expone usuario y contraseña tal como se construyó")
    void exponeUsuarioYContrasenaTalComoSeConstruyo() {
        LoginCommand command = new LoginCommand("juan.perez", "secret");

        assertThat(command.username()).isEqualTo("juan.perez");
        assertThat(command.password()).isEqualTo("secret");
    }

    @Test
    @DisplayName("Dos instancias con los mismos valores son iguales")
    void dosInstanciasConLosMismosValoresSonIguales() {
        assertThat(new LoginCommand("a", "b")).isEqualTo(new LoginCommand("a", "b"));
    }
}