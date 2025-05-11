package org.terning.terningserver.common.security.jwt.auth;

import org.terning.terningserver.common.security.jwt.exception.JwtErrorCode;
import org.terning.terningserver.common.security.jwt.exception.JwtException;

public class UserIdConverter {

    public static Long convertToLong(Object value) {
        if (value instanceof Long) {
            return (Long) value;
        }
        if (value instanceof String) {
            return parseLongSafely((String) value);
        }
        throw new JwtException(JwtErrorCode.INVALID_USER_ID);
    }

    private static Long parseLongSafely(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new JwtException(JwtErrorCode.INVALID_USER_ID);
        }
    }
}
