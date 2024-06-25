package org.terning.terningserver.exception;

import lombok.Getter;
import org.terning.terningserver.exception.enums.ErrorMessage;

@Getter
public class CustomException extends RuntimeException {

    private ErrorMessage errorMessage;

    public CustomException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

}
