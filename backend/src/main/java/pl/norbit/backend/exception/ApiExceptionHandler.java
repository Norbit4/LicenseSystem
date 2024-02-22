package pl.norbit.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.norbit.backend.exception.model.LastTokenException;
import pl.norbit.backend.exception.model.NotValidLicenseException;
import pl.norbit.backend.exception.model.NotValidTokenException;
import pl.norbit.backend.exception.model.RequestException;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<ApiException> handleFileException(RequestException e){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(e.getMessage(),status, ZonedDateTime.now(ZoneId.systemDefault()));

        return new ResponseEntity<>(apiException, status);
    }

    @ExceptionHandler(value = NotValidLicenseException.class)
    public ResponseEntity<ApiException> handleNotValidTokenException(NotValidLicenseException e){
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        ApiException apiException = new ApiException(e.getMessage(),status, ZonedDateTime.now(ZoneId.systemDefault()));

        return new ResponseEntity<>(apiException, status );
    }

    @ExceptionHandler(value = NotValidTokenException.class)
    public ResponseEntity<ApiException> handleNotValidTokenException(NotValidTokenException e){
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        ApiException apiException = new ApiException(e.getMessage(),status, ZonedDateTime.now(ZoneId.systemDefault()));

        return new ResponseEntity<>(apiException, status );
    }

    @ExceptionHandler(value = LastTokenException.class)
    public ResponseEntity<ApiException> handleLastTokenException(LastTokenException e){
        HttpStatus status = HttpStatus.CONFLICT;

        ApiException apiException = new ApiException(e.getMessage(),status, ZonedDateTime.now(ZoneId.systemDefault()));

        return new ResponseEntity<>(apiException, status );
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiException> handleOther(RuntimeException e){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiException apiException = new ApiException(e.getMessage(), status, ZonedDateTime.now(ZoneId.systemDefault()));

        return new ResponseEntity<>(apiException, status);
    }
}
