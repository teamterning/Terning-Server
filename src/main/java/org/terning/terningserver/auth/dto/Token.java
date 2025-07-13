package org.terning.terningserver.auth.dto;

public record Token(String accessToken, String refreshToken) {
}
