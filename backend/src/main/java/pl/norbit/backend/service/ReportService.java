package pl.norbit.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
        List<License> licenses = licenseService.findAll();
        List<Token> tokens = tokenService.findAll();

        return excelService.getExcelFile(licenses, tokens);
    }
}
