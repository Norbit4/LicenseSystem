package pl.norbit.backend.service;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.norbit.backend.dto.license.CreatedLicenseDTO;
import pl.norbit.backend.dto.license.LicenseResponseDTO;
import pl.norbit.backend.dto.license.LicenseServerKeyDTO;
import pl.norbit.backend.exception.model.LicenseNotFoundException;
import pl.norbit.backend.exception.model.NotValidLicenseException;
import pl.norbit.backend.exception.model.RequestException;
import pl.norbit.backend.mapper.LicenseMapper;
import pl.norbit.backend.model.license.License;
import pl.norbit.backend.model.license.LicenseType;
import pl.norbit.backend.repository.LicenseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static pl.norbit.backend.helper.LicenseHelper.*;

@SpringBootTest
class LicenseServiceTest {

    @Mock
    private LicenseRepository licenseRepository;
    @InjectMocks
    private LicenseService underTest;
    @Mock
    private LicenseMapper licenseMapper;

    @Test
    @DisplayName("Should throw request exception when owner is null")
    void save_should_throw_requestException() {
        //given
        CreatedLicenseDTO createdLicenseDTO = new CreatedLicenseDTO(
                null,
                "Some license product",
                3
        );

        // when
        // than
        assertThrows(RequestException.class, () -> underTest.save(createdLicenseDTO));
    }

    @Test
    @DisplayName("Should save license with default license type and generate license key")
    void save_should_save_default_license() {
        //given
        CreatedLicenseDTO createdLicenseDTO = new CreatedLicenseDTO(
                "owner",
                "description",
                3
        );

        //when
        underTest.save(createdLicenseDTO);

        //then
        ArgumentCaptor<License> licenseArgumentCaptor = ArgumentCaptor.forClass(License.class);

        verify(licenseRepository, times(1)).save(licenseArgumentCaptor.capture());

        License capturedLicense = licenseArgumentCaptor.getValue();

        assertEquals(createdLicenseDTO.owner(), capturedLicense.getOwner());
        assertEquals(createdLicenseDTO.description(), capturedLicense.getDescription());
        assertEquals(LicenseType.DEFAULT, capturedLicense.getLicenseType());
        assertNotNull(capturedLicense.getLicenseKey());
    }

    @Test
    @DisplayName("Should save license when description is null")
    void save_should_save_license_when_description_is_null() {
        //given
        CreatedLicenseDTO createdLicenseDTO = new CreatedLicenseDTO(
                "owner",
                null,
                3
        );

        //when
        underTest.save(createdLicenseDTO);

        //then
        ArgumentCaptor<License> licenseArgumentCaptor = ArgumentCaptor.forClass(License.class);

        verify(licenseRepository, times(1)).save(licenseArgumentCaptor.capture());

        License capturedLicense = licenseArgumentCaptor.getValue();

        assertEquals(createdLicenseDTO.owner(), capturedLicense.getOwner());
        assertNull(capturedLicense.getDescription());
        assertEquals(LicenseType.DEFAULT, capturedLicense.getLicenseType());
        assertNotNull(capturedLicense.getLicenseKey());
    }

    @Test
    @DisplayName("Should save license with lifetime license type and generate license key")
    void save_should_save_lifetime_license() {
        //given
        CreatedLicenseDTO createdLicenseDTO = new CreatedLicenseDTO(
                "owner",
                "description",
                0
        );

        //when
        underTest.save(createdLicenseDTO);

        //then
        ArgumentCaptor<License> licenseArgumentCaptor = ArgumentCaptor.forClass(License.class);

        verify(licenseRepository, times(1)).save(licenseArgumentCaptor.capture());

        License capturedLicense = licenseArgumentCaptor.getValue();

        assertEquals(createdLicenseDTO.owner(), capturedLicense.getOwner());
        assertEquals(createdLicenseDTO.description(), capturedLicense.getDescription());
        assertEquals(LicenseType.LIFETIME, capturedLicense.getLicenseType());
        assertNotNull(capturedLicense.getLicenseKey());
    }

    @Test
    @DisplayName("Should generate new server key")
    void generateServerKey_should_generate_new_server_key() {
        //given
        License license = createLifetimeLicense();
        String licenseKey = license.getLicenseKey();
        String serverKey = license.getServerKey();

        License licenseWithNewServerKey = getLicenseWithNewServerKey(license);

        LicenseServerKeyDTO licenseServerKeyDTO = new LicenseServerKeyDTO(licenseKey, licenseWithNewServerKey.getServerKey());

        given(licenseRepository.findByLicenseKey(licenseKey)).willReturn(Optional.of(license));
        given(licenseRepository.save(any(License.class))).willReturn(licenseWithNewServerKey);
        given(licenseMapper.entityToServerKeyDto(licenseWithNewServerKey)).willReturn(licenseServerKeyDTO);

        //when
        LicenseServerKeyDTO actualDTO = underTest.generateServerKey(licenseKey);

        //then
        verify(licenseRepository, times(1)).save(license);

        assertNotEquals(serverKey, actualDTO.serverKey());
    }

    @Test
    @DisplayName("Should throw LicenseNotFoundException when license not found")
    void findByLicenseKey_should_throw_LicenseNotFoundException_when_license_not_found() {
        //given
        String licenseKey = "wrongLicenseKey";

        given(licenseRepository.findByLicenseKey(licenseKey)).willReturn(Optional.empty());

        //when
        //then
        assertThrows(LicenseNotFoundException.class, () -> underTest.generateServerKey(licenseKey));
    }

    @Test
    @DisplayName("Should not throw when server key is valid")
    void isValidServerKey_should_not_throw() {
        //given

        License license = createLifetimeLicense();

        String licenseKey = license.getLicenseKey();
        String serverKey = license.getServerKey();

        LicenseServerKeyDTO licenseServerKeyDTO = new LicenseServerKeyDTO(licenseKey, serverKey);

        given(licenseRepository.findByLicenseKey(licenseServerKeyDTO.licenseKey())).willReturn(Optional.of(license));

        //when
        //then
        assertDoesNotThrow(() -> underTest.isValidServerKey(licenseServerKeyDTO));

        verify(licenseRepository, times(1)).findByLicenseKey(licenseServerKeyDTO.licenseKey());
    }

    @Test
    @DisplayName("Should throw RequestException when server key is null")
    void isValidServerKey_should_throw_RequestException_when_server_key_is_null() {
        //given
        String licenseKey = "licenseKey";

        LicenseServerKeyDTO licenseServerKeyDTO = new LicenseServerKeyDTO(licenseKey, null);

        //when
        //then
        assertThrows(RequestException.class, () -> underTest.isValidServerKey(licenseServerKeyDTO));
    }

    @Test
    @DisplayName("Should throw RequestException when license key is null")
    void isValidServerKey_should_throw_RequestException_when_license_key_is_null() {
        //given
        String serverKey = "serverKey";

        LicenseServerKeyDTO licenseServerKeyDTO = new LicenseServerKeyDTO(null, serverKey);

        //when
        //then
        assertThrows(RequestException.class, () -> underTest.isValidServerKey(licenseServerKeyDTO));
    }

    @Test
    @DisplayName("Should throw LicenseNotFoundException when license not found")
    void isValidServerKey_should_throw_LicenseNotFoundException_when_license_not_found_() {
        //given
        String licenseKey = "wrongLicenseKey";
        String serverKey = "serverKey";

        LicenseServerKeyDTO licenseServerKeyDTO = new LicenseServerKeyDTO(licenseKey, serverKey);

        given(licenseRepository.findByLicenseKey(licenseKey)).willReturn(Optional.empty());

        //when
        //then
        assertThrows(LicenseNotFoundException.class, () -> underTest.isValidServerKey(licenseServerKeyDTO));
    }

    @Test
    @DisplayName("Should throw NotValidLicenseException when server key is wrong")
    void isValidServerKey_should_throw_NotValidLicenseException_when_server_key_is_wrong() {
        //given
        License license = createLifetimeLicense();

        String wrongServerKey = "wrongServerKey";
        LicenseServerKeyDTO licenseServerKeyDTO = new LicenseServerKeyDTO(license.getLicenseKey(), wrongServerKey);

        given(licenseRepository.findByLicenseKey(licenseServerKeyDTO.licenseKey())).willReturn(Optional.of(license));

        //when
        //then
        assertThrows(NotValidLicenseException.class, () -> underTest.isValidServerKey(licenseServerKeyDTO));
    }

    @Test
    @DisplayName("Should not throw when license is valid")
    void isValidKey_should_not_throw() {
        //given
        License license = createLifetimeLicense();
        String licenseKey = license.getLicenseKey();

        given(licenseRepository.findByLicenseKey(licenseKey)).willReturn(Optional.of(license));

        //when
        //then
        assertDoesNotThrow(() -> underTest.isValidKey(licenseKey));

        verify(licenseRepository, times(1)).findByLicenseKey(licenseKey);
    }

    @Test
    @DisplayName("Should throw LicenseNotFoundException when license not found")
    void isValidKey_should_throw_LicenseNotFoundException_when_license_not_found() {
        //given
        String licenseKey = UUID.randomUUID().toString();

        given(licenseRepository.findByLicenseKey(licenseKey)).willReturn(Optional.empty());

        //when
        //then
        assertThrows(LicenseNotFoundException.class, () -> underTest.isValidKey(licenseKey));
    }

    @Test
    @DisplayName("Should throw NotValidLicenseException when license is expired")
    void isValidKey_should_throw_NotValidLicenseException_when_license_is_expired() {
        //given
        License license = createExpiredLicense();
        String licenseKey = license.getLicenseKey();

        given(licenseRepository.findByLicenseKey(licenseKey)).willReturn(Optional.of(license));

        //when
        //then
        assertThrows(NotValidLicenseException.class, () -> underTest.isValidKey(licenseKey));
    }

    @Test
    @DisplayName("Should delete license by id")
    void deleteById_should_delete_license_by_id() {
        //given
        License license = createLifetimeLicense();

        Long id = license.getId();

        given(licenseRepository.findById(id)).willReturn(Optional.of(license));

        //when
        underTest.deleteById(id);

        //then
        verify(licenseRepository, times(1)).findById(id);
        verify(licenseRepository, times(1)).delete(license);
    }

    @Test
    @DisplayName("Should throw RequestException when license not found")
    void deleteById_should_throw_RequestException() {
        //given
        Long id = 1L;

        given(licenseRepository.findById(id)).willReturn(Optional.empty());

        //when
        //then
        assertThrows(RequestException.class, () -> underTest.deleteById(id));
    }

    @Test
    @DisplayName("Should get all licenses")
    void getAll_should_get_all_licenses() {
        //given
        License license = createLifetimeLicense();
        List<License> licenses = List.of(license);

        LicenseResponseDTO licenseResponseDTO = new LicenseResponseDTO(
                license.getId(),
                license.getLicenseKey(),
                license.getOwner(),
                license.getDescription(),
                license.getLicenseType(),
                license.getCreationDate(),
                license.getExpirationDate()
        );

        given(licenseRepository.findAll()).willReturn(licenses);
        given(licenseMapper.entityToDto(license)).willReturn(licenseResponseDTO);

        //when
        List<LicenseResponseDTO> allLicenses = underTest.getAll();

        //then
        verify(licenseRepository, times(1)).findAll();

        assertEquals(1, allLicenses.size());
        assertEquals(licenseResponseDTO, allLicenses.get(0));
    }

    @Test
    @DisplayName("Should delete all")
    void deleteAll_should_delete_all_licences() {
        //when
        underTest.deleteAll();

        //then
        verify(licenseRepository, times(1)).deleteAll();
    }
}