package pl.norbit.backend.service;

import org.springframework.stereotype.Service;
import pl.norbit.backend.exception.LastTokenException;
import pl.norbit.backend.exception.RequestException;
import pl.norbit.backend.model.Token;
import pl.norbit.backend.model.TokenType;
import pl.norbit.backend.repository.TokenRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public List<Token> findByTokenType(TokenType tokenType) {
        return tokenRepository.findTokensByTokenType(tokenType.name());
    }

    public void save(Token token) {
        String tokenTypeString = token.getTokenType();

        if(tokenTypeString  == null) throw new RequestException("Token type cannot be null");

        try {
            TokenType.valueOf(tokenTypeString.toUpperCase());
        }
        catch (IllegalArgumentException e){
            String message = "Token type " + tokenTypeString +" is not valid! Valid values: " + Arrays.toString(TokenType.values());

            throw new RequestException(message);
        }

        tokenRepository.save(token);
    }

    public void deleteById(String id) {
        Token token = tokenRepository.findById(id)
                .orElseThrow(() -> new RequestException("Token with id " + id + " does not exist!"));

        if(token.getTokenType().equals(TokenType.ADMIN.name())){
            List<Token> adminTokens = findByTokenType(TokenType.ADMIN);

            if(adminTokens.size() == 1) throw new LastTokenException("You cannot delete the last ADMIN token!");
        }

        tokenRepository.deleteById(id);
    }

    public List<Token> findAll() {
        return tokenRepository.findAll();
    }
}
