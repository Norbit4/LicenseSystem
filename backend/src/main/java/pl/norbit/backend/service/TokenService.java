package pl.norbit.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.norbit.backend.exception.LastTokenException;
import pl.norbit.backend.exception.NotValidTokenException;
import pl.norbit.backend.exception.RequestException;
import pl.norbit.backend.model.Token;
import pl.norbit.backend.model.TokenType;
import pl.norbit.backend.repository.TokenRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public List<Token> findByTokenType(TokenType tokenType) {
        return tokenRepository.findTokensByTokenType(tokenType);
    }

    public void verifyToken(String token, TokenType expectedTokenType) {
        Token tokenEntity = tokenRepository.findTokenByAccessToken(token)
                .orElseThrow(() -> new NotValidTokenException("Wrong token!"));

        TokenType tokenType = tokenEntity.getTokenType();

        if(tokenType == TokenType.ADMIN) return;

        if (tokenType != expectedTokenType) {
            throw new NotValidTokenException(("Token type is not valid! Expected: " + expectedTokenType.name()));
        }
    }
    public Token create(String type) {
        if(type == null) throw new RequestException("Token request cannot be null!");

        Token token = new Token();

        TokenType tokenType;

        try {
            tokenType = TokenType.valueOf(type.toUpperCase());
        }
        catch (IllegalArgumentException e){
            String message = "Token type " + type  +" is not valid! Valid values: " + Arrays.toString(TokenType.values());

            throw new RequestException(message);
        }
        token.setCreationDate(System.currentTimeMillis());
        token.setAccessToken(UUID.randomUUID().toString());
        token.setTokenType(tokenType);

        tokenRepository.save(token);

        return token;
    }

    public void deleteById(Long id) {
        Token token = tokenRepository.findById(id)
                .orElseThrow(() -> new RequestException("Token with id " + id + " does not exist!"));

        if(token.getTokenType() == TokenType.ADMIN){
            List<Token> adminTokens = findByTokenType(TokenType.ADMIN);

            if(adminTokens.size() == 1) throw new LastTokenException("You cannot delete the last ADMIN token!");
        }

        tokenRepository.deleteById(id);
    }

    public List<Token> findAll() {
        return tokenRepository.findAll();
    }
}
