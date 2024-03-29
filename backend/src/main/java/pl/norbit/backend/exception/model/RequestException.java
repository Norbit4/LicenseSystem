package pl.norbit.backend.exception.model;


import pl.norbit.backend.exception.ExceptionMessage;

public class RequestException extends RuntimeException{

    public RequestException(ExceptionMessage exceptionMessage){
        super(exceptionMessage.getMessage());
    }
}
