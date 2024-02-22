package pl.norbit.backend.exception.model;


import pl.norbit.backend.exception.ExceptionMessage;

public class RequestException extends RuntimeException{

    public RequestException(String message){
        super(message);
    }

    public RequestException(ExceptionMessage exceptionMessage){
        super(exceptionMessage.getMessage());
    }
    public RequestException(String message, Throwable throwable){
        super(message, throwable);
    }
}
