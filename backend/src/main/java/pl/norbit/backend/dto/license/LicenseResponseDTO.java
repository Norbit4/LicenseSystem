package pl.norbit.backend.dto.license;

import lombok.Builder;
import pl.norbit.backend.model.license.LicenseType;

@Builder
public record LicenseResponseDTO(Long id,
                                 String licenseKey,
                                 String owner,
                                 String description,
                                 LicenseType licenseType,
                                 long expirationDate,
                                 long creationDate) {
}
