package pl.norbit.backend.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.norbit.backend.dto.license.LicenseResponseDTO;
import pl.norbit.backend.dto.token.TokenResponseDTO;
import pl.norbit.backend.model.license.License;
import pl.norbit.backend.model.token.Token;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pl.norbit.backend.helper.LicenseHelper.createLifetimeLicense;
import static pl.norbit.backend.helper.TokenHelper.createDefaultToken;

@SpringBootTest
class ExcelServiceTest {

    @Autowired
    private ExcelService underTest;

    @Test
    @DisplayName("Should generate excel file")
    void getExcelFile_should_generate_excel_file() {
        //given
        License license = createLifetimeLicense();

        LicenseResponseDTO licenseResponseDTO = new LicenseResponseDTO(
                license.getId(),
                license.getLicenseKey(),
                license.getOwner(),
                license.getDescription(),
                license.getLicenseType(),
                license.getCreationDate(),
                license.getExpirationDate()
        );

        List<LicenseResponseDTO> licenses = new ArrayList<>();
        licenses.add(licenseResponseDTO);

        Token token = createDefaultToken();

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(
                token.getId(),
                token.getAccessToken(),
                token.getCreationDate(),
                token.getTokenType()
        );

        List<TokenResponseDTO> tokens = new ArrayList<>();
        tokens.add(tokenResponseDTO);

        //when
        byte[] excelData = underTest.getExcelFile(licenses, tokens);

        //then
        assertNotNull(excelData);
        assertTrue(excelData.length > 0);
    }

    @Test
    @DisplayName("Should not throw exception when licenses list is empty")
    void getExcelFile_should_throw_exception_when_licenses_list_is_empty() {
        //given
        List<LicenseResponseDTO> licenses = new ArrayList<>();
        List<TokenResponseDTO> tokens = new ArrayList<>();

        //when
        //then
        assertDoesNotThrow(() -> underTest.getExcelFile(licenses, tokens));
    }
}