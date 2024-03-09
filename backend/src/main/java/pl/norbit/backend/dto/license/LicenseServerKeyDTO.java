package pl.norbit.backend.dto.license;

import lombok.Builder;

@Builder
public record LicenseServerKeyDTO(String licenseKey,
                                  String serverKey) {
}
