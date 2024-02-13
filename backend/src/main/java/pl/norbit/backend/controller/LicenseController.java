package pl.norbit.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.norbit.backend.model.License;
import pl.norbit.backend.service.LicenseService;

@RestController
@RequestMapping("/api/v1/license")
public class LicenseController {
    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }


    @PostMapping(path = "/save")
    public ResponseEntity<?> saveTicket(@RequestBody License license) {

        return new ResponseEntity<>(license, HttpStatus.ACCEPTED);
    }

}
