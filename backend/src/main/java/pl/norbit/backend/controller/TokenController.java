package pl.norbit.backend.controller;

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
public class TokenController {
    private final TokenService tokenService;

    @PostMapping(path = CREATE)
    public ResponseEntity<TokenResponseDTO> saveToken(@RequestHeader("Authorization") String token, @PathVariable String type) {
        tokenService.verifyToken(token, TokenType.ADMIN);

        return new ResponseEntity<>(tokenService.create(type), HttpStatus.OK);
    }

    @DeleteMapping(path = DELETE_BY_ID)
    public ResponseEntity<HttpStatus> deleteToken(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        tokenService.verifyToken(token, TokenType.ADMIN);

        tokenService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = GET_ALL)
    public ResponseEntity<List<TokenResponseDTO>> getAllTokens(@RequestHeader("Authorization") String token) {
        tokenService.verifyToken(token, TokenType.ADMIN);

        return new ResponseEntity<>(tokenService.getAll(), HttpStatus.OK);
    }

    static final class Routes {
        static final String ROOT = "/api/v1/token";
        static final String CREATE = "/create/{type}";
        static final String DELETE_BY_ID = "/delete/{id}";
        static final String GET_ALL = "/get/all";
    }
}
