package com.jtb.loyalty.domain.auth.response;

public record TokenResponse(String accessToken, String tokenType, long expiresIn) {
}
