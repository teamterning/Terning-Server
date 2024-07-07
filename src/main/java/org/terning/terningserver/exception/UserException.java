package org.terning.terningserver.exception;

import lombok.Getter;
import org.terning.terningserver.exception.enums.ErrorMessage;

@Getter
public class UserException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public UserException(ErrorMessage errorMessage) {
        super("[UserException] : " + errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
