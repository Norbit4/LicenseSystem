package pl.norbit.backend.dto.license;

import lombok.Builder;

@Builder
public record CreatedLicenseDTO(String owner,
                                String description,
                                int expireDays,
                                String licenseType){
}

