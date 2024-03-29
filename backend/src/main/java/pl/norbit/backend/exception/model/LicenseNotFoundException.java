package pl.norbit.backend.exception.model;

import pl.norbit.backend.exception.ExceptionMessage;

public class LicenseNotFoundException extends RuntimeException{

    public LicenseNotFoundException(ExceptionMessage exceptionMessage){
        super(exceptionMessage.getMessage());
    }
}
