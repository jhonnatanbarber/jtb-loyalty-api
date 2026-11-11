package zcee.jbt.error.exception;

import com.fasterxml.jackson.core.JacksonException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import zcee.jbt.error.config.ErrorCatalog;
import zcee.jbt.error.config.ErrorResponseConfig;
import zcee.jbt.error.shared.ErrorCode;
import zcee.jbt.error.shared.ErrorType;

import java.util.stream.Collectors;

/**
 * Convierte cualquier excepción de la API al formato estándar de error.
 */
@ApplicationScoped
@Slf4j
public class GlobalExceptionHandler {

    @Inject
    ErrorCatalog errorCatalog;

    @Inject
    ErrorResponseConfig responseConfig;

    /** Errores de negocio: tipo, código y mensaje salen del catálogo en application.yml. */
    @ServerExceptionMapper
    public Response handleBusiness(BusinessException ex, UriInfo uriInfo) {
        var definition = errorCatalog.get(ex.getErrorCode());
        String description = ex.getDetail() != null ? ex.getDetail() : definition.message();

        log.warn("Error de negocio {} ({}) en {}: {}",
                ex.getErrorCode(), definition.code(), uriInfo.getPath(), description);

        return build(statusOf(definition.type()), definition.code(), definition.type(),
                ex.getErrorCode().name(), description);
    }

    @ServerExceptionMapper
    public Response handleValidation(ConstraintViolationException ex, UriInfo uriInfo) {
        String fields = ex.getConstraintViolations().stream()
                .map(v -> lastNode(v) + ": " + v.getMessage())
                .sorted()
                .collect(Collectors.joining("; "));

        log.warn("Validación fallida en {}: {}", uriInfo.getPath(), fields);
        return fromCatalog(ErrorCode.VALIDATION_ERROR, fields);
    }

    @ServerExceptionMapper
    public Response handleMalformedBody(JacksonException ex, UriInfo uriInfo) {
        log.warn("Cuerpo inválido en {}: {}", uriInfo.getPath(), ex.getOriginalMessage());
        return fromCatalog(ErrorCode.MALFORMED_BODY, null);
    }

    @ServerExceptionMapper
    public Response handleWebApplication(WebApplicationException ex, UriInfo uriInfo) {
        int status = ex.getResponse().getStatus();
        ErrorType type = switch (status) {
            case 401 -> ErrorType.UNAUTHORIZED;
            case 404 -> ErrorType.NOT_FOUND;
            case 409 -> ErrorType.CONFLICT;
            default -> status >= 500 ? ErrorType.INTERNAL : ErrorType.VALIDATION;
        };
        return build(status, "HTTP" + status, type, "HTTP_ERROR", ex.getMessage());
    }

    @ServerExceptionMapper
    public Response handleUnexpected(Exception ex, UriInfo uriInfo) {
        log.error("Error inesperado en {}", uriInfo.getPath(), ex);
        return fromCatalog(ErrorCode.INTERNAL_ERROR, null);
    }

    private Response fromCatalog(ErrorCode errorCode, String customDescription) {
        var definition = errorCatalog.get(errorCode);
        String description = customDescription != null ? customDescription : definition.message();
        return build(statusOf(definition.type()), definition.code(), definition.type(),
                errorCode.name(), description);
    }

    private Response build(int status, String code, ErrorType type,
                           String detailType, String detailDescription) {
        var body = new ErrorResponse(
                code,
                type,
                responseConfig.description(),
                new ErrorResponse.Detail(detailType, detailDescription, responseConfig.component()));

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }

    private static int statusOf(ErrorType type) {
        return switch (type) {
            case VALIDATION -> 400;
            case UNAUTHORIZED -> 401;
            case NOT_FOUND -> 404;
            case CONFLICT -> 409;
            case BUSINESS_RULE -> 422;
            case INTERNAL -> 500;
        };
    }

    /** De "login.request.username" deja solo "username". */
    private static String lastNode(ConstraintViolation<?> violation) {
        String field = null;
        for (Path.Node node : violation.getPropertyPath()) {
            field = node.getName();
        }
        return field;
    }
}