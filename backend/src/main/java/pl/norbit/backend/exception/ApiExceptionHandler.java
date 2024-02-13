package pl.norbit.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<?> handleFileException(RequestException e){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(e.getMessage(),status, ZonedDateTime.now(ZoneId.systemDefault()));

        return new ResponseEntity<>(apiException, status );
    }

    @ExceptionHandler(value = LastTokenException.class)
    public ResponseEntity<?> handleLastTokenException(LastTokenException e){
        HttpStatus status = HttpStatus.CONFLICT;

        ApiException apiException = new ApiException(e.getMessage(),status, ZonedDateTime.now(ZoneId.systemDefault()));

        return new ResponseEntity<>(apiException, status );
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> handleOther(RuntimeException e){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiException apiException = new ApiException(e.getMessage(), status,
                ZonedDateTime.now(ZoneId.systemDefault()));

        return new ResponseEntity<>(apiException, status);
    }
}
