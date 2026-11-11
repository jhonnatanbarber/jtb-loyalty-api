package zcee.jbt.error.config;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import zcee.jbt.error.shared.ErrorCode;

import java.util.Arrays;
import java.util.List;

/**
 * Catálogo de errores de la API.
 * <p>
 * Expone la definición (tipo, código y mensaje) de cada {@link ErrorCode},
 * tomada de application.yml → app.errors.&lt;CLAVE&gt;.
 */
@ApplicationScoped
@Slf4j
public class ErrorCatalog {

    @Inject
    ErrorCatalogConfig config;

    /**
     * Al arrancar, verifica que cada ErrorCode tenga su bloque en application.yml.
     * Si falta alguno, la aplicación no arranca: así un error mal configurado
     * se detecta al desplegar y no cuando un usuario lo provoca.
     */
    void validate(@Observes StartupEvent event) {
        List<String> missing = Arrays.stream(ErrorCode.values())
                .map(Enum::name)
                .filter(key -> !config.errors().containsKey(key))
                .toList();

        if (!missing.isEmpty()) {
            throw new IllegalStateException(
                    "Faltan errores en application.yml (app.errors): " + missing);
        }
        log.info("Catálogo de errores cargado: {} definiciones", config.errors().size());
    }

    public ErrorCatalogConfig.ErrorDefinition get(ErrorCode errorCode) {
        ErrorCatalogConfig.ErrorDefinition definition = config.errors().get(errorCode.name());
        if (definition == null) {
            // Solo ocurriría si se saltó la validación de arranque (por ejemplo, en un test).
            throw new IllegalStateException(
                    "No existe definición para el error " + errorCode + " en app.errors");
        }
        return definition;
    }
}