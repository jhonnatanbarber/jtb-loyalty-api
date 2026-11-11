package zcee.jbt.error.shared;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ErrorTypeTest {

    @Test
    @DisplayName("Define las seis categorías de error")
    void defineLasSeisCategoriasDeError() {
        assertThat(ErrorType.values()).containsExactly(
                ErrorType.VALIDATION,
                ErrorType.UNAUTHORIZED,
                ErrorType.NOT_FOUND,
                ErrorType.CONFLICT,
                ErrorType.BUSINESS_RULE,
                ErrorType.INTERNAL);
    }
}
