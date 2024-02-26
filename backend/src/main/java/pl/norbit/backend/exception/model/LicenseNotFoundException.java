package pl.norbit.backend.exception.model;

import pl.norbit.backend.exception.ExceptionMessage;

public class LicenseNotFoundException extends RuntimeException{

    public LicenseNotFoundException(String message){
        super(message);
    }
    public LicenseNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }

    public LicenseNotFoundException(ExceptionMessage exceptionMessage){
        super(exceptionMessage.getMessage());
    }
}
