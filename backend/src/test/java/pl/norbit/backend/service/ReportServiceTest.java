package pl.norbit.backend.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.norbit.backend.repository.TokenRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
class ReportServiceTest {

    @Mock
    private LicenseService licenseService;
    @Mock
    private TokenService tokenService;
    @Mock
    private ExcelService excelService;
    @InjectMocks
    private ReportService reportService;
    @Test
    @DisplayName("Should get report file")
    void should_get_report_file() {
        //given
        byte[] excelData = new byte[0];

        given(licenseService.getAll()).willReturn(new ArrayList<>());
        given(tokenService.getAll()).willReturn(new ArrayList<>());
        given(excelService.getExcelFile(anyList(), anyList())).willReturn(excelData);

        //when
        byte[] reportFile = reportService.getReportFile();
        //then
        assertNotNull(reportFile);
        assertEquals(excelData, reportFile);
    }
}