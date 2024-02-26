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

import static pl.norbit.backend.controller.LicenseController.Routes.*;

@RestController
@RequestMapping(path = ROOT)
@AllArgsConstructor
public class LicenseController {
    private final LicenseService licenseService;
    private final TokenService tokenService;

    @PostMapping(path = SAVE)
    public ResponseEntity<License> saveTicket(@RequestHeader("token") String token, @RequestBody License license) {
        tokenService.verifyToken(token, TokenType.ADMIN);

        return new ResponseEntity<>(licenseService.save(license), HttpStatus.OK);
    }

    @PutMapping(path = UPDATE_ACTIVE)
    public ResponseEntity<License> updateActive(@RequestHeader("token") String token, @RequestBody License license) {
        tokenService.verifyToken(token, TokenType.DEFAULT);

        return new ResponseEntity<>(licenseService.updateActive(license), HttpStatus.OK);
    }

    @GetMapping(path = IS_VALID)
    public ResponseEntity<HttpStatus> isValid(@RequestHeader("token") String token, @PathVariable String key) {
        tokenService.verifyToken(token, TokenType.DEFAULT);

        licenseService.isValid(key);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = DELETE_BY_ID)
    public ResponseEntity<HttpStatus> deleteTicket(@RequestHeader("token") String token, @PathVariable Long id) {
        tokenService.verifyToken(token, TokenType.ADMIN);

        licenseService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @JsonView(License.Get.class)
    @GetMapping(path = GET_ALL)
    public ResponseEntity<List<License>> getAllLicenses(@RequestHeader("token") String token) {
        tokenService.verifyToken(token, TokenType.ADMIN);

        return new ResponseEntity<>(licenseService.findAll(), HttpStatus.OK);
    }

    static final class Routes {
        static final String ROOT = "/api/v1/license";
        static final String SAVE = "/save";
        static final String UPDATE_ACTIVE = "/update";
        static final String IS_VALID = "/isValid/{key}";
        static final String DELETE_BY_ID = "/delete/{id}";
        static final String GET_ALL = "/get/all";
    }
}
