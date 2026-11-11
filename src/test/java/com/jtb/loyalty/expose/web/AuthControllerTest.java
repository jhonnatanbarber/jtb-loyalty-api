package com.jtb.loyalty.expose.web;

import com.jtb.loyalty.application.auth.LoginUseCase;
import com.jtb.loyalty.application.auth.dto.LoginCommand;
import com.jtb.loyalty.application.auth.dto.TokenView;
import com.jtb.loyalty.domain.auth.request.LoginRequest;
import com.jtb.loyalty.domain.auth.response.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    LoginUseCase loginUseCase;

    @InjectMocks
    AuthController controller;

    @Test
    @DisplayName("Login delega en LoginUseCase y mapea el resultado a TokenResponse")
    void login_delegaEnLoginUseCaseYMapeaElResultado() {
        when(loginUseCase.execute(new LoginCommand("juan.perez", "secret")))
                .thenReturn(new TokenView("jwt-abc", "Bearer", 3600L));

        TokenResponse response = controller.login(new LoginRequest("juan.perez", "secret"));

        assertThat(response.accessToken()).isEqualTo("jwt-abc");
        assertThat(response.tokenType()).isEqualTo("Bearer");
        assertThat(response.expiresIn()).isEqualTo(3600L);
    }

    @Test
    @DisplayName("Login normaliza el usuario (quita espacios) antes de ejecutar el caso de uso")
    void login_normalizaElUsuarioAntesDeEjecutarElCasoDeUso() {
        when(loginUseCase.execute(any(LoginCommand.class)))
                .thenReturn(new TokenView("jwt-abc", "Bearer", 3600L));

        controller.login(new LoginRequest("  juan.perez  ", "secret"));

        ArgumentCaptor<LoginCommand> captor = ArgumentCaptor.forClass(LoginCommand.class);
        verify(loginUseCase).execute(captor.capture());
        assertThat(captor.getValue().username()).isEqualTo("juan.perez");
        assertThat(captor.getValue().password()).isEqualTo("secret");
    }
}
