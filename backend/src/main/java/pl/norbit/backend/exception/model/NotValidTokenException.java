package pl.norbit.backend.exception.model;

import pl.norbit.backend.exception.ExceptionMessage;

public class NotValidTokenException extends RuntimeException{
    public NotValidTokenException(String message){
        super(message);
    }

    public NotValidTokenException(ExceptionMessage exceptionMessage){
        super(exceptionMessage.getMessage());
    }
    public NotValidTokenException(String message, Throwable throwable){
        super(message, throwable);
    }
}
