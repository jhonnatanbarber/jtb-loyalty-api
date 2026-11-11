package com.jtb.loyalty.application.auth;

import com.jtb.loyalty.application.auth.dto.LoginCommand;
import com.jtb.loyalty.application.auth.dto.TokenView;
import com.jtb.loyalty.domain.auth.exception.InvalidCredentialsException;
import com.jtb.loyalty.domain.auth.repository.UserRepository;
import com.jtb.loyalty.domain.auth.service.PasswordHasher;
import com.jtb.loyalty.domain.user.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Caso de uso: iniciar sesión en el panel.
 * <p>
 * Valida usuario y contraseña y devuelve un JWT. Ante cualquier fallo responde
 * siempre con el mismo error, para no revelar si el usuario existe.
 */
@ApplicationScoped
@Slf4j
public class LoginUseCase {
    private static final String TOKEN_TYPE = "Bearer";

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordHasher passwordHasher;

    @Inject
    TokenIssuer tokenIssuer;

    @Inject
    DummyPasswordHasher dummyPasswordHasher;

    public TokenView execute(LoginCommand request) {
        log.info("Intento de inicio de sesión para el usuario '{}'", request.username());
        Optional<User> found = userRepository.findByUsername(request.username());
        if (found.isEmpty()) {
            dummyPasswordHasher.match(request.password());
            log.error("Inicio de sesión fallido: usuario '{}' no existe", request.username());
            throw new InvalidCredentialsException();
        }
        log.info("Se encontró correctamente el usuario '{}'", found.get().getUsername());
        User user = found.get();
        if (!passwordHasher.matches(request.password(), user.getPasswordHash())) {
            log.error("Inicio de sesión fallido: contraseña incorrecta para '{}'", request.username());
            throw new InvalidCredentialsException();
        }
        if (!user.isActive()) {
            log.error("Inicio de sesión rechazado: usuario '{}' está desactivado", request.username());
            throw new InvalidCredentialsException();
        }

        String accessToken = tokenIssuer.issue(user);
        log.info("Inicio de sesión exitoso: usuario '{}' - perfil: ({})", user.getUsername(), user.getRole());
        return new TokenView(accessToken, TOKEN_TYPE, tokenIssuer.expiresInSeconds());
    }
}
