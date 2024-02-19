package pl.norbit.backend.exception;

public class NotValidTokenException extends RuntimeException{
    public NotValidTokenException(String message){
        super(message);
    }
    public NotValidTokenException(String message, Throwable throwable){
        super(message, throwable);
    }
}
