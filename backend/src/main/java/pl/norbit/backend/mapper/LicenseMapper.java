package pl.norbit.backend.mapper;

import org.springframework.stereotype.Component;
import pl.norbit.backend.dto.license.LicenseResponseDTO;
import pl.norbit.backend.dto.license.LicenseServerKeyDTO;
import pl.norbit.backend.model.license.License;

@Component
public class LicenseMapper {
    public LicenseResponseDTO entityToDto(License entity){
        if (entity == null) {
            return null;
        }

        return LicenseResponseDTO.builder()
                .id(entity.getId())
                .licenseKey(entity.getLicenseKey())
                .owner(entity.getOwner())
                .description(entity.getDescription())
                .licenseType(entity.getLicenseType())
                .creationDate(entity.getCreationDate())
                .expirationDate(entity.getExpirationDate())
                .build();
    }

    public LicenseServerKeyDTO entityToServerKeyDto(License entity){
        if (entity == null) {
            return null;
        }

        return LicenseServerKeyDTO.builder()
                .licenseKey(entity.getLicenseKey())
                .serverKey(entity.getServerKey())
                .build();
    }
}
