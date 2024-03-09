package pl.norbit.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.norbit.backend.model.token.TokenType;
import pl.norbit.backend.service.ReportService;
import pl.norbit.backend.service.TokenService;

import static pl.norbit.backend.controller.ReportController.Routes.GENERATE;
import static pl.norbit.backend.controller.ReportController.Routes.ROOT;

@RestController
@RequestMapping(path = ROOT)
@AllArgsConstructor
public class ReportController {
    private final TokenService tokenService;
    private final ReportService reportService;

    @GetMapping(path = GENERATE)
    public ResponseEntity<byte[]> generate(@RequestHeader("Authorization") String token){
        tokenService.verifyToken(token, TokenType.ADMIN);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.xlsx\"")
                .body(reportService.getReportFile());
    }
    static final class Routes {
        static final String ROOT = "/api/v1/report";
        static final String GENERATE = "/generate/excel";
    }
}
