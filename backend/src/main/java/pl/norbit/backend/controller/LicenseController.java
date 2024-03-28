package pl.norbit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.norbit.backend.dto.license.CreatedLicenseDTO;
import pl.norbit.backend.dto.license.LicenseResponseDTO;
import pl.norbit.backend.dto.license.LicenseServerKeyDTO;
import pl.norbit.backend.model.token.TokenType;
import pl.norbit.backend.service.LicenseService;
import pl.norbit.backend.service.TokenService;

import java.util.List;

import static pl.norbit.backend.controller.LicenseController.Routes.*;

@RestController
@RequestMapping(path = ROOT)
@AllArgsConstructor
@Tag(name = "License", description = "Endpoints related to licenses.")
public class LicenseController {
    private final LicenseService licenseService;

    @PostMapping(path = SAVE)
    public ResponseEntity<LicenseResponseDTO> saveTicket(@RequestBody CreatedLicenseDTO license) {
        return new ResponseEntity<>(licenseService.save(license), HttpStatus.OK);
    }

    @GetMapping(path = GENERATE_SERVER_KEY)
    public ResponseEntity<LicenseServerKeyDTO> generateServerKey(@PathVariable String licenseKey){
        return new ResponseEntity<>(licenseService.generateServerKey(licenseKey), HttpStatus.OK);
    }

    @GetMapping(path = IS_VALID)
    public ResponseEntity<HttpStatus> isValidLicense(@PathVariable String licenseKey) {
        licenseService.isValidKey(licenseKey);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = IS_VALID_SERVER_KEY)
    public ResponseEntity<HttpStatus> isValidServerKey(@RequestBody LicenseServerKeyDTO license) {
        licenseService.isValidServerKey(license);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = DELETE_BY_ID)
    public ResponseEntity<HttpStatus> deleteTicket(@PathVariable Long id) {
        licenseService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = GET_ALL)
    public ResponseEntity<List<LicenseResponseDTO>> getAllLicenses() {
        return new ResponseEntity<>(licenseService.getAll(), HttpStatus.OK);
    }

    static final class Routes {
        static final String ROOT = "/api/v1/license";
        static final String SAVE = "/save";
        static final String GENERATE_SERVER_KEY = "/generateServerKey/{licenseKey}";
        static final String IS_VALID = "/isValid/{licenseKey}";
        static final String IS_VALID_SERVER_KEY = "/isValidServerKey";
        static final String DELETE_BY_ID = "/delete/{id}";
        static final String GET_ALL = "/get/all";
    }
}
