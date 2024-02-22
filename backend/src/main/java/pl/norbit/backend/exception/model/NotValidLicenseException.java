package pl.norbit.backend.exception.model;

import pl.norbit.backend.exception.ExceptionMessage;

public class NotValidLicenseException extends RuntimeException{
    public NotValidLicenseException(String message){
        super(message);
    }
    public NotValidLicenseException(ExceptionMessage exceptionMessage){
        super(exceptionMessage.getMessage());
    }
    public NotValidLicenseException(String message, Throwable throwable){
        super(message, throwable);
    }
}
