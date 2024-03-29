package pl.norbit.backend.exception.model;

import pl.norbit.backend.exception.ExceptionMessage;

public class LastTokenException extends RuntimeException{

    public LastTokenException(ExceptionMessage exceptionMessage){
        super(exceptionMessage.getMessage());
    }
}
