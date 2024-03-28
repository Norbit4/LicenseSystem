package pl.norbit.backend.helper;

import pl.norbit.backend.model.license.License;
import pl.norbit.backend.model.license.LicenseType;

import java.util.UUID;

public class LicenseHelper {

    public static License createLifetimeLicense() {
        return License.builder()
                .id(1L)
                .owner("John Doe")
                .licenseKey(UUID.randomUUID().toString())
                .description("Test license")
                .creationDate(System.currentTimeMillis())
                .expirationDate(0L)
                .lastActive(System.currentTimeMillis())
                .licenseType(LicenseType.LIFETIME)
                .serverKey(UUID.randomUUID().toString())
                .build();
    }
    public static License getLicenseWithNewServerKey(License license) {
        return License.builder()
                .id(license.getId())
                .owner(license.getOwner())
                .licenseKey(license.getLicenseKey())
                .description(license.getDescription())
                .creationDate(license.getCreationDate())
                .expirationDate(license.getExpirationDate())
                .lastActive(license.getLastActive())
                .licenseType(license.getLicenseType())
                .serverKey(UUID.randomUUID().toString())
                .build();
    }

    public static License createExpiredLicense() {
        return License.builder()
                .id(1L)
                .owner("John Doe")
                .licenseKey(UUID.randomUUID().toString())
                .description("Test license")
                .creationDate(System.currentTimeMillis())
                .expirationDate(System.currentTimeMillis() - 1000)
                .lastActive(System.currentTimeMillis())
                .licenseType(LicenseType.DEFAULT)
                .serverKey(UUID.randomUUID().toString())
                .build();
    }
}
