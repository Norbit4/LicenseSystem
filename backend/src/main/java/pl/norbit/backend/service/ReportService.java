package pl.norbit.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.norbit.backend.dto.license.LicenseResponseDTO;
import pl.norbit.backend.dto.token.TokenResponseDTO;
import pl.norbit.backend.model.license.License;
import pl.norbit.backend.model.token.Token;

import java.util.List;


@Service
@AllArgsConstructor
public class ReportService {

    private final LicenseService licenseService;
    private final TokenService tokenService;
    private final ExcelService excelService;

    public byte[] getReportFile(){
        List<LicenseResponseDTO> licenses = licenseService.getAll();
        List<TokenResponseDTO> tokens = tokenService.getAll();

        return excelService.getExcelFile(licenses, tokens);
    }
}
