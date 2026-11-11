package zcee.jbt.error.shared;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ErrorCodeTest {

    @Test
    @DisplayName("Define los dieciséis códigos de error de negocio")
    void defineLosDieciseisCodigosDeErrorDeNegocio() {
        assertThat(ErrorCode.values()).hasSize(16);
    }

    @Test
    @DisplayName("valueOf resuelve cada constante")
    void valueOfResuelveCadaConstante() {
        for (ErrorCode code : ErrorCode.values()) {
            assertThat(ErrorCode.valueOf(code.name())).isEqualTo(code);
        }
    }
}
