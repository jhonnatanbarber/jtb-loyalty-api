package zcee.jbt.error.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "app.error-response")
public interface ErrorResponseConfig {
    String description();
    String component();
}