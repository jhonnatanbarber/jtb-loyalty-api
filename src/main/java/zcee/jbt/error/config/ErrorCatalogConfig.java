package zcee.jbt.error.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithParentName;
import zcee.jbt.error.shared.ErrorType;

import java.util.Map;

@ConfigMapping(prefix = "app.errors")
public interface ErrorCatalogConfig {

    @WithParentName
    Map<String, ErrorDefinition> errors();

    interface ErrorDefinition {
        ErrorType type();
        String code();
        String message();
    }
}
