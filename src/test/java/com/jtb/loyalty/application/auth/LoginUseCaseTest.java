package com.jtb.loyalty.application.auth;

import com.jtb.loyalty.application.auth.dto.LoginCommand;
import com.jtb.loyalty.application.auth.dto.TokenView;
import com.jtb.loyalty.domain.auth.exception.InvalidCredentialsException;
import com.jtb.loyalty.domain.auth.repository.UserRepository;
import com.jtb.loyalty.domain.auth.service.PasswordHasher;
import com.jtb.loyalty.domain.user.model.Role;
import com.jtb.loyalty.domain.user.model.User;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordHasher passwordHasher;

    @Mock
    TokenIssuer tokenIssuer;

    @Mock
    DummyPasswordHasher dummyPasswordHasher;

    @InjectMocks
    LoginUseCase loginUseCase;

    private User activeUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .username("juan.perez")
                .displayName("Juan Perez")
                .passwordHash("hash-guardado")
                .role(Role.BARBER)
                .active(true)
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .build();
    }

    @Test
    @DisplayName("execute usa el hash señuelo y lanza InvalidCredentialsException cuando el usuario no existe")
    void execute_usaElHashSenuelo_yLanzaInvalidCredentialsException_cuandoElUsuarioNoExiste() {
        when(userRepository.findByUsername("juan.perez")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loginUseCase.execute(new LoginCommand("juan.perez", "secret")))
                .isInstanceOf(InvalidCredentialsException.class);

        verify(dummyPasswordHasher).match("secret");
        verify(passwordHasher, never()).matches(any(), any());
    }

    @Test
    @DisplayName("execute lanza InvalidCredentialsException cuando la contraseña no coincide")
    void execute_lanzaInvalidCredentialsException_cuandoLaContrasenaNoCoincide() {
        User user = activeUser();
        when(userRepository.findByUsername("juan.perez")).thenReturn(Optional.of(user));
        when(passwordHasher.matches("secret", "hash-guardado")).thenReturn(false);

        assertThatThrownBy(() -> loginUseCase.execute(new LoginCommand("juan.perez", "secret")))
                .isInstanceOf(InvalidCredentialsException.class);

        verify(dummyPasswordHasher, never()).match(any());
    }

    @Test
    @DisplayName("execute lanza InvalidCredentialsException cuando el usuario está desactivado")
    void execute_lanzaInvalidCredentialsException_cuandoElUsuarioEstaDesactivado() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .username("juan.perez")
                .displayName("Juan Perez")
                .passwordHash("hash-guardado")
                .role(Role.BARBER)
                .active(false)
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .build();
        when(userRepository.findByUsername("juan.perez")).thenReturn(Optional.of(user));
        when(passwordHasher.matches("secret", "hash-guardado")).thenReturn(true);

        assertThatThrownBy(() -> loginUseCase.execute(new LoginCommand("juan.perez", "secret")))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    @DisplayName("execute devuelve el token emitido cuando las credenciales son correctas")
    void execute_devuelveElTokenEmitido_cuandoLasCredencialesSonCorrectas() {
        User user = activeUser();
        when(userRepository.findByUsername("juan.perez")).thenReturn(Optional.of(user));
        when(passwordHasher.matches("secret", "hash-guardado")).thenReturn(true);
        when(tokenIssuer.issue(user)).thenReturn("jwt-abc");
        when(tokenIssuer.expiresInSeconds()).thenReturn(300L);

        TokenView result = loginUseCase.execute(new LoginCommand("juan.perez", "secret"));

        assertThat(result.accessToken()).isEqualTo("jwt-abc");
        assertThat(result.tokenType()).isEqualTo("Bearer");
        assertThat(result.expiresIn()).isEqualTo(300L);
    }
}
