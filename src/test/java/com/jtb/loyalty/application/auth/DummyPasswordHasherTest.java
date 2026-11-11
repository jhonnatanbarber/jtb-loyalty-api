package com.jtb.loyalty.application.auth;

import com.jtb.loyalty.domain.auth.service.PasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DummyPasswordHasherTest {

    @Mock
    PasswordHasher passwordHasher;

    @InjectMocks
    DummyPasswordHasher dummyPasswordHasher;

    @BeforeEach
    void setUp() {
        when(passwordHasher.hash(any())).thenReturn("hash-senuelo");
        dummyPasswordHasher.init();
    }

    @Test
    @DisplayName("init genera el hash señuelo una sola vez a partir de un valor aleatorio")
    void init_generaElHashSenuelo_apartirDeUnValorAleatorio() {
        verify(passwordHasher).hash(any());
    }

    @Test
    @DisplayName("match delega en PasswordHasher usando el hash señuelo generado al iniciar")
    void match_delegaEnPasswordHasher_usandoElHashSenueloGeneradoAlIniciar() {
        dummyPasswordHasher.match("cualquier-clave");

        verify(passwordHasher).matches("cualquier-clave", "hash-senuelo");
    }
}
