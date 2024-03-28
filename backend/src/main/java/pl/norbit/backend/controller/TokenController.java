package pl.norbit.backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.norbit.backend.dto.token.TokenResponseDTO;
import pl.norbit.backend.model.token.TokenType;
import pl.norbit.backend.service.TokenService;

import java.util.List;

import static pl.norbit.backend.controller.TokenController.Routes.*;

@RestController
@RequestMapping(path = ROOT)
@AllArgsConstructor
@Tag(name = "Token", description = "Endpoints related to token.")
public class TokenController {
    private final TokenService tokenService;

    @PostMapping(path = CREATE)
    public ResponseEntity<TokenResponseDTO> saveToken(@PathVariable String type) {
        return new ResponseEntity<>(tokenService.create(type), HttpStatus.OK);
    }

    @DeleteMapping(path = DELETE_BY_ID)
    public ResponseEntity<HttpStatus> deleteToken(@PathVariable Long id) {
        tokenService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = GET_ALL)
    public ResponseEntity<List<TokenResponseDTO>> getAllTokens() {
        return new ResponseEntity<>(tokenService.getAll(), HttpStatus.OK);
    }

    static final class Routes {
        static final String ROOT = "/api/v1/token";
        static final String CREATE = "/create/{type}";
        static final String DELETE_BY_ID = "/delete/{id}";
        static final String GET_ALL = "/get/all";
    }
}
