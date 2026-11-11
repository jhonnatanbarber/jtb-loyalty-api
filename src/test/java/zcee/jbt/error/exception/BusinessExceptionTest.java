package zcee.jbt.error.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import zcee.jbt.error.shared.ErrorCode;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BusinessExceptionTest {

    @Test
    @DisplayName("Constructor con solo ErrorCode usa el nombre del código como mensaje")
    void constructorConSoloErrorCode_usaElNombreDelCodigoComoMensaje() {
        BusinessException exception = new BusinessException(ErrorCode.USER_NOT_FOUND);

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_FOUND);
        assertThat(exception.getMessage()).isEqualTo("USER_NOT_FOUND");
        assertThat(exception.getDetail()).isNull();
    }

    @Test
    @DisplayName("Constructor con detalle lo expone como mensaje y como detail")
    void constructorConDetalle_loExponeComoMensajeYComoDetail() {
        BusinessException exception = new BusinessException(ErrorCode.CARD_FULL, "La tarjeta ya está llena");

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.CARD_FULL);
        assertThat(exception.getMessage()).isEqualTo("La tarjeta ya está llena");
        assertThat(exception.getDetail()).isEqualTo("La tarjeta ya está llena");
    }
}
