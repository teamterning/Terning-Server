package org.terning.terningserver.jwt.auth;

public class TokenExtractor {
    public static final String TOKEN_PREFIX = "Bearer ";

    private TokenExtractor() {
        throw new IllegalStateException("Utility class");
    }

    public static String extractToken(String token) {
        return token.replaceFirst(TOKEN_PREFIX, "").trim();
    }
}
