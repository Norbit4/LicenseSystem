package pl.norbit.backend.exception.model;

import pl.norbit.backend.exception.ExceptionMessage;

public class ExcelDataException extends RuntimeException {

    public ExcelDataException(String message){
        super(message);
    }

    public ExcelDataException(ExceptionMessage exceptionMessage){
        super(exceptionMessage.getMessage());
    }
    public ExcelDataException(String message, Throwable throwable){
        super(message, throwable);
    }
}
