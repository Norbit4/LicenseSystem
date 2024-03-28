package pl.norbit.backend.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.norbit.backend.dto.license.LicenseResponseDTO;
import pl.norbit.backend.dto.license.LicenseServerKeyDTO;
import pl.norbit.backend.model.license.License;

import static org.junit.jupiter.api.Assertions.*;
import static pl.norbit.backend.helper.LicenseHelper.createLifetimeLicense;

@SpringBootTest
class LicenseMapperTest {

    @Autowired
    private LicenseMapper underTest;

    @Test
    @DisplayName("Should map License entity to LicenseResponseDTO")
    void should_map_entity_to_dto() {
        //given
        License license = createLifetimeLicense();

        LicenseResponseDTO licenseResponseDTO = new LicenseResponseDTO(
                license.getId(),
                license.getLicenseKey(),
                license.getOwner(),
                license.getDescription(),
                license.getLicenseType(),
                license.getExpirationDate(),
                license.getCreationDate());

        //when
        LicenseResponseDTO actualResponseDTO = underTest.entityToDto(license);

        //then
        assertEquals(actualResponseDTO, licenseResponseDTO);
    }

    @Test
    @DisplayName("Should return null when entity is null")
    void entityToDto_should_return_null_when_entity_is_null() {
        //given
        //when
        LicenseResponseDTO actualResponseDTO = underTest.entityToDto(null);

        //then
        assertNull(actualResponseDTO);
    }

    @Test
    @DisplayName("Should map License entity to LicenseServerKeyDTO")
    void should_map_entity_to_server_key_dto() {
        //given
        License license = createLifetimeLicense();
        String licenseKey = license.getLicenseKey();
        String serverKey = license.getServerKey();

        LicenseServerKeyDTO licenseServerKeyDTO = new LicenseServerKeyDTO(licenseKey, serverKey);

        //when
        LicenseServerKeyDTO actualServerKeyDTO = underTest.entityToServerKeyDto(license);

        //then
        assertEquals(actualServerKeyDTO, licenseServerKeyDTO);
    }
    @Test
    @DisplayName("Should return null when entity is null")
    void entityToServerKeyDto_should_return_null_when_entity_is_null() {
        //given
        //when
        LicenseServerKeyDTO actualServerKeyDTO = underTest.entityToServerKeyDto(null);

        //then
        assertNull(actualServerKeyDTO);
    }
}