package pl.norbit.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.norbit.backend.model.license.License;
import pl.norbit.backend.model.token.TokenType;
import pl.norbit.backend.service.LicenseService;
import pl.norbit.backend.service.TokenService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/license")
@AllArgsConstructor
public class LicenseController {
    private final LicenseService licenseService;
    private final TokenService tokenService;

    @PostMapping(path = "/save")
    public ResponseEntity<License> saveTicket(@RequestHeader("token") String token, @RequestBody License license) {
        tokenService.verifyToken(token, TokenType.ADMIN);

        License saveLicense = licenseService.save(license);

        return new ResponseEntity<>(saveLicense, HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<HttpStatus> updateTime(@RequestHeader("token") String token, @RequestBody License license) {
        tokenService.verifyToken(token, TokenType.DEFAULT);

        licenseService.updateActive(license);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/isValid/{key}")
    public ResponseEntity<HttpStatus> isValid(@RequestHeader("token") String token, @PathVariable String key) {
        tokenService.verifyToken(token, TokenType.DEFAULT);

        licenseService.isValid(key);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteTicket(@RequestHeader("token") String token, @PathVariable Long id) {
        tokenService.verifyToken(token, TokenType.ADMIN);

        licenseService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @JsonView(License.Get.class)
    @GetMapping(path = "/get/all")
    public ResponseEntity<List<License>> getAllLicenses(@RequestHeader("token") String token) {
        tokenService.verifyToken(token, TokenType.ADMIN);

        List<License> all = licenseService.findAll();

        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
