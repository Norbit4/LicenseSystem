package pl.norbit.backend.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.norbit.backend.model.license.License;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static pl.norbit.backend.helper.LicenseHelper.createLifetimeLicense;

@SpringBootTest
class LicenseRepositoryTest {

    @Autowired
    private LicenseRepository underTest;

    @Test
    @DisplayName("Should find License by licenseKey")
    void should_find_License_by_license_key() {
        //given
        License license = createLifetimeLicense();
        String licenseKey = license.getLicenseKey();

        underTest.save(license);

        //when
        Optional<License> optionalLicense = underTest.findByLicenseKey(licenseKey);

        //then
        assertTrue(optionalLicense.isPresent());
    }
}