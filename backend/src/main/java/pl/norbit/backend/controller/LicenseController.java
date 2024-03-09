package pl.norbit.backend.controller;

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
public class LicenseController {
    private final LicenseService licenseService;
    private final TokenService tokenService;

    @PostMapping(path = SAVE)
    public ResponseEntity<LicenseResponseDTO> saveTicket(@RequestHeader("Authorization") String token,
                                                         @RequestBody CreatedLicenseDTO license) {
        tokenService.verifyToken(token, TokenType.ADMIN);

        return new ResponseEntity<>(licenseService.save(license), HttpStatus.OK);
    }

    @GetMapping(path = GENERATE_SERVER_KEY)
    public ResponseEntity<LicenseServerKeyDTO> generateServerKey(@RequestHeader("Authorization") String token,
                                                                 @PathVariable String licenseKey){
        tokenService.verifyToken(token, TokenType.DEFAULT);

        return new ResponseEntity<>(licenseService.generateServerKey(licenseKey), HttpStatus.OK);
    }

    @GetMapping(path = IS_VALID)
    public ResponseEntity<HttpStatus> isValidLicense(@RequestHeader("Authorization") String token,
                                                     @PathVariable String licenseKey) {
        tokenService.verifyToken(token, TokenType.DEFAULT);

        licenseService.isValidKey(licenseKey);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = IS_VALID_SERVER_KEY)
    public ResponseEntity<HttpStatus> isValidServerKey(@RequestHeader("Authorization") String token,
                                                       @RequestBody LicenseServerKeyDTO license) {

        tokenService.verifyToken(token, TokenType.DEFAULT);

        licenseService.isValidServerKey(license);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = DELETE_BY_ID)
    public ResponseEntity<HttpStatus> deleteTicket(@RequestHeader("Authorization") String token,
                                                   @PathVariable Long id) {
        tokenService.verifyToken(token, TokenType.ADMIN);

        licenseService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = GET_ALL)
    public ResponseEntity<List<LicenseResponseDTO>> getAllLicenses(@RequestHeader("Authorization") String token) {
        tokenService.verifyToken(token, TokenType.ADMIN);

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
