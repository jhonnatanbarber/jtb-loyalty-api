package zcee.jbt.error.exception;

import zcee.jbt.error.shared.ErrorType;

public record ErrorResponse(
        String code,
        ErrorType type,
        String description,
        Detail detail) {

    public record Detail(
            String type,
            String description,
            String component) {
    }
}