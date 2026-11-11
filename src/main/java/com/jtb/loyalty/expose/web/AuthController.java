package com.jtb.loyalty.expose.web;

import com.jtb.loyalty.application.auth.ChangePasswordUseCase;
import com.jtb.loyalty.application.auth.LoginUseCase;
import com.jtb.loyalty.application.auth.mapper.AuthMapper;
import com.jtb.loyalty.application.auth.provider.CurrentUserProvider;
import com.jtb.loyalty.application.auth.queries.GetCurrentUserQuery;
import com.jtb.loyalty.domain.auth.request.ChangePasswordRequest;
import com.jtb.loyalty.domain.auth.request.LoginRequest;
import com.jtb.loyalty.domain.auth.response.CurrentUserResponse;
import com.jtb.loyalty.domain.auth.response.TokenResponse;
import com.jtb.loyalty.shared.roles.Roles;
import com.jtb.loyalty.shared.utils.Constants;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/auth")
@ApplicationScoped
@RolesAllowed({Roles.ADMIN, Roles.BARBER})
@Slf4j
public class AuthController {
    @Inject
    LoginUseCase loginUseCase;
/*    @Inject
    GetCurrentUserQuery getCurrentUserQuery;
    @Inject
    ChangePasswordUseCase changePasswordUseCase;
    @Inject
    CurrentUserProvider currentUserProvider;*/

    @POST
    @Path("")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TokenResponse login(
            @Valid @NotNull(message = Constants.MESSAGE_VALIDATION_NOTNULL_REQUEST_AUTH) LoginRequest request) {
        return AuthMapper.toResponse(loginUseCase.execute(AuthMapper.toCommand(request)));
    }

/*    @GET
    @Path("/provider")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CurrentUserResponse provider() {
        return AuthMapper.toResponse(
                getCurrentUserQuery.execute(currentUserProvider.currentUserId()));
    }

    @POST
    @Path("/change-password")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<Void> changePassword(
            @Valid @NotNull(message = "El cuerpo de la solicitud es obligatorio") ChangePasswordRequest request) {
        var userId = currentUserProvider.currentUserId();
        changePasswordUseCase.execute(AuthMapper.toCommand(request, userId));
        //log.info("Contraseña actualizada para el usuario {}", userId);
        return RestResponse.noContent();
    }*/
}
