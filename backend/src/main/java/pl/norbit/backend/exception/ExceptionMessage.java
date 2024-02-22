package pl.norbit.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    LICENSE_NOT_FOUND("License not found"),
    LICENSE_NOT_VALID("License is not valid"),
    LICENSE_IS_EXPIRED("License is expired"),
    LICENSE_KEY_NULL("License key cannot be null"),
    LICENSE_SERVER_KEY_NULL("Server key cannot be null"),
    LICENSE_OWNER_NULL("Owner cannot be null"),
    LICENSE_WRONG_SERVER_KEY ("Server key is not valid"),
    TOKEN_NOT_FOUND("Token not found"),
    TOKEN_NOT_VALID("Token is not valid"),
    TOKEN_TYPE_NOT_VALID("Token type is not valid"),
    TOKEN_ADMIN_LAST("You cannot delete the last ADMIN token!");

    private final String message;
}
