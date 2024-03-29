package pl.norbit.backend.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.norbit.backend.exception.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@SpringBootTest
class ApiExceptionHandlerTest {

    @Autowired
    private ApiExceptionHandler underTest;

    @Test
    @DisplayName("Should handle LicenseNotFoundException")
    void should_handleLicenseNotFoundException() {
        //given
        LicenseNotFoundException mockException = mock(LicenseNotFoundException.class);

        //when
        ResponseEntity<ApiException> response = underTest.handleLicenseNotFoundException(mockException);

        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Should handle ExcelDataException")
    void should_handleExcelDataException() {
        //given
        ExcelDataException mockException = mock(ExcelDataException.class);

        //when
        ResponseEntity<ApiException> response = underTest.handleExcelDataException(mockException);

        //then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Should handle RequestException")
    void should_handleFileException() {
        //given
        RequestException mockException = mock(RequestException.class);

        //when
        ResponseEntity<ApiException> response = underTest.handleFileException(mockException);

        //then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Should handle NotValidLicenseException")
    void should_handleNotValidTokenException() {
        //given
        NotValidLicenseException mock = mock(NotValidLicenseException.class);

        //when
        ResponseEntity<ApiException> response = underTest.handleNotValidTokenException(mock);

        //then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Should handle LastTokenException")
    void should_handleLastTokenException() {
        //given
        LastTokenException mock = mock(LastTokenException.class);

        //when
        ResponseEntity<ApiException> response = underTest.handleLastTokenException(mock);

        //then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Should handle RuntimeException")
    void should_handleOther() {
        //given
        RuntimeException mock = mock(RuntimeException.class);

        //when
        ResponseEntity<ApiException> response = underTest.handleOther(mock);

        //then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}