package pl.norbit.backend.exception;

public class LastTokenException extends RuntimeException{

    public LastTokenException(String message){
        super(message);
    }
    public LastTokenException(String message, Throwable throwable){
        super(message, throwable);
    }
}
