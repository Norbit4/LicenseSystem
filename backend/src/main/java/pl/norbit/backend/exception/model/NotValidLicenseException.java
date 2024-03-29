package pl.norbit.backend.exception.model;

import pl.norbit.backend.exception.ExceptionMessage;

public class NotValidLicenseException extends RuntimeException{
    public NotValidLicenseException(ExceptionMessage exceptionMessage){
        super(exceptionMessage.getMessage());
    }
}
