package pl.norbit.backend.exception;


public class RequestException extends RuntimeException{

    public RequestException(String message){
        super(message);
    }
    public RequestException(String message, Throwable throwable){
        super(message, throwable);
    }
}
