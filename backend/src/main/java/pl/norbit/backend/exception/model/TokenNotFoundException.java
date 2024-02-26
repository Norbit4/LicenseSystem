package pl.norbit.backend.exception.model;

import pl.norbit.backend.exception.ExceptionMessage;

public class TokenNotFoundException extends RuntimeException{

    public TokenNotFoundException(ExceptionMessage exceptionMessage){
        super(exceptionMessage.getMessage());
    }

    public TokenNotFoundException(String message){
        super(message);
    }

    public TokenNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }
}
