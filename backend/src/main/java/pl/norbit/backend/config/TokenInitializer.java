package pl.norbit.backend.config;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.norbit.backend.model.token.Token;
import pl.norbit.backend.model.token.TokenType;
import pl.norbit.backend.repository.TokenRepository;

import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
public class TokenInitializer implements CommandLineRunner {

    private final TokenRepository tokenRepository;

    /*  Initialize default admin token if not exist

        DEFAULT ADMIN TOKEN: admin-secret-token
    */
    @Override
    public void run(String... args) {
        List<Token> tokens = tokenRepository.findTokensByTokenType(TokenType.ADMIN);

        if(!tokens.isEmpty()) return;

        Token token = new Token();
        token.setTokenType(TokenType.ADMIN);
        token.setAccessToken("admin-secret-token");
        token.setCreationDate(System.currentTimeMillis());

        log.info("Default admin token not exist. Creating default admin token...");
        tokenRepository.save(token);
    }
}
