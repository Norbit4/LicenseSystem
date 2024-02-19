package pl.norbit.backend.controller;


import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.norbit.backend.model.Token;
import pl.norbit.backend.model.TokenType;
import pl.norbit.backend.service.TokenService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/token")
@AllArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping(path = "/create/{type}")
    public ResponseEntity<?> saveTicket(@RequestHeader("token") String token, @PathVariable String type) {
        tokenService.verifyToken(token, TokenType.ADMIN);

        Token saveToken = tokenService.create(type);

        return new ResponseEntity<>(saveToken, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteTicket(@RequestHeader("token") String token, @PathVariable Long id) {
        tokenService.verifyToken(token, TokenType.ADMIN);

        tokenService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/get/all")
    @JsonView(Token.TokenGet.class)
    public ResponseEntity<?> getAllTokens(@RequestHeader("token") String token) {
        tokenService.verifyToken(token, TokenType.ADMIN);

        List<Token> all = tokenService.findAll();

        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
