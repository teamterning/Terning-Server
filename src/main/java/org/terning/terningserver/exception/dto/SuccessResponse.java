package org.terning.terningserver.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.terning.terningserver.exception.enums.SuccessMessage;

@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public record SuccessResponse<T>(
        boolean isSuccess,
        int code,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T result
) {
    public static <T> SuccessResponse of(SuccessMessage successMessage){
        return new SuccessResponse(successMessage.isSuccess(), successMessage.getStatus(), successMessage.getMessage(), null);
    }

    public static <T> SuccessResponse of(SuccessMessage successMessage, T result){
        return new SuccessResponse(successMessage.isSuccess(), successMessage.getStatus(), successMessage.getMessage(), result);
    }
}
