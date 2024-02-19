package pl.norbit.backend.exception;

public class NotValidLicenseException extends RuntimeException{
    public NotValidLicenseException(String message){
        super(message);
    }
    public NotValidLicenseException(String message, Throwable throwable){
        super(message, throwable);
    }
}
