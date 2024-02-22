package pl.norbit.backend.exception.model;

import pl.norbit.backend.exception.ExceptionMessage;

public class LastTokenException extends RuntimeException{

    public LastTokenException(String message){
        super(message);
    }

    public LastTokenException(ExceptionMessage exceptionMessage){
        super(exceptionMessage.getMessage());
    }
    public LastTokenException(String message, Throwable throwable){
        super(message, throwable);
    }
}
