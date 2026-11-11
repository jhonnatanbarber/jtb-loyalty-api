package com.jtb.loyalty.application.auth.mapper;

import com.jtb.loyalty.application.auth.dto.ChangePasswordCommand;
import com.jtb.loyalty.application.auth.dto.CurrentUserView;
import com.jtb.loyalty.application.auth.dto.LoginCommand;
import com.jtb.loyalty.application.auth.dto.TokenView;
import com.jtb.loyalty.domain.auth.request.ChangePasswordRequest;
import com.jtb.loyalty.domain.auth.request.LoginRequest;
import com.jtb.loyalty.domain.auth.response.CurrentUserResponse;
import com.jtb.loyalty.domain.auth.response.TokenResponse;
import com.jtb.loyalty.domain.user.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AuthMapperTest {

    @Test
    @DisplayName("toCommand normaliza espacios del usuario y deja la contraseña intacta")
    void toCommand_normalizaEspaciosDelUsuario_yDejaLaContrasenaIntacta() {
        LoginRequest request = new LoginRequest("  juan.perez  ", " secret ");

        LoginCommand command = AuthMapper.toCommand(request);

        assertThat(command.username()).isEqualTo("juan.perez");
        assertThat(command.password()).isEqualTo(" secret ");
    }

    @Test
    @DisplayName("toCommand de ChangePassword incluye el userId del token")
    void toCommand_deChangePassword_incluyeElUserIdDelToken() {
        UUID userId = UUID.randomUUID();
        ChangePasswordRequest request = new ChangePasswordRequest("actual", "nueva12345");

        ChangePasswordCommand command = AuthMapper.toCommand(request, userId);

        assertThat(command.userId()).isEqualTo(userId);
        assertThat(command.currentPassword()).isEqualTo("actual");
        assertThat(command.newPassword()).isEqualTo("nueva12345");
    }

    @Test
    @DisplayName("toResponse de TokenView mapea los tres campos")
    void toResponse_deTokenView_mapeaLosTresCampos() {
        TokenView view = new TokenView("jwt-abc", "Bearer", 3600L);

        TokenResponse response = AuthMapper.toResponse(view);

        assertThat(response.accessToken()).isEqualTo("jwt-abc");
        assertThat(response.tokenType()).isEqualTo("Bearer");
        assertThat(response.expiresIn()).isEqualTo(3600L);
    }

    @Test
    @DisplayName("toResponse de CurrentUserView mapea los cuatro campos")
    void toResponse_deCurrentUserView_mapeaLosCuatroCampos() {
        UUID id = UUID.randomUUID();
        CurrentUserView view = new CurrentUserView(id, "juan.perez", "Juan Perez", Role.ADMIN);

        CurrentUserResponse response = AuthMapper.toResponse(view);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.username()).isEqualTo("juan.perez");
        assertThat(response.displayName()).isEqualTo("Juan Perez");
        assertThat(response.role()).isEqualTo(Role.ADMIN);
    }
}
