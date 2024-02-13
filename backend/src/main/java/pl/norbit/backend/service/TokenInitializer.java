package pl.norbit.backend.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.norbit.backend.model.Token;
import pl.norbit.backend.model.TokenType;

import java.util.List;

@Component
public class TokenInitializer implements CommandLineRunner {
    private final TokenService tokenService;

    public TokenInitializer(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    @Override
    public void run(String... args) {
        List<Token> tokens = tokenService.findByTokenType(TokenType.ADMIN);

        if(!tokens.isEmpty()) return;

        Token token = new Token();
        token.setTokenType(TokenType.ADMIN.name());
        token.setAccessToken("admin-secret-token");

        tokenService.save(token);
    }
}
