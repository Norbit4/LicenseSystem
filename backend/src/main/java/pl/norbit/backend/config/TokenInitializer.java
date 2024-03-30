package pl.norbit.backend.config;

import io.github.cdimascio.dotenv.Dotenv;
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

    private Dotenv dotenv;

    /*  Initialize default admin token if not exist

        DEFAULT ADMIN TOKEN: admin-secret-token
    */
    @Override
    public void run(String... args) {
        List<Token> tokens = tokenRepository.findTokensByTokenType(TokenType.ADMIN);

        if(!tokens.isEmpty()) return;

        log.info("Default admin token not exist. Creating default admin token...");
        String defaultAccessToken = dotenv.get("DEFAULT_ACCESS_TOKEN");

        Token token = new Token();
        token.setTokenType(TokenType.ADMIN);
        token.setAccessToken(defaultAccessToken);
        token.setCreationDate(System.currentTimeMillis());

        tokenRepository.save(token);
        log.info("Created default admin token: " + defaultAccessToken);
    }
}
