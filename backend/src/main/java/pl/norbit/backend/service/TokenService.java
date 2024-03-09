package pl.norbit.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.norbit.backend.dto.token.TokenResponseDTO;
import pl.norbit.backend.exception.ExceptionMessage;
import pl.norbit.backend.exception.model.LastTokenException;
import pl.norbit.backend.exception.model.NotValidTokenException;
import pl.norbit.backend.exception.model.RequestException;
import pl.norbit.backend.exception.model.TokenNotFoundException;
import pl.norbit.backend.mapper.TokenMapper;
import pl.norbit.backend.model.token.Token;
import pl.norbit.backend.model.token.TokenType;
import pl.norbit.backend.repository.TokenRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final TokenMapper tokenMapper;

    public List<Token> findByTokenType(TokenType tokenType) {
        return tokenRepository.findTokensByTokenType(tokenType);
    }

    public void verifyToken(String token, TokenType expectedTokenType) {
        Token tokenEntity = tokenRepository.findTokenByAccessToken(token)
                .orElseThrow(() -> new TokenNotFoundException(ExceptionMessage.TOKEN_NOT_VALID));

        TokenType tokenType = tokenEntity.getTokenType();

        if(tokenType == TokenType.ADMIN) return;

        if (tokenType != expectedTokenType) {
            throw new NotValidTokenException(ExceptionMessage.TOKEN_TYPE_NOT_VALID);
        }
    }
    public TokenResponseDTO create(String type) {
        Token token = new Token();

        TokenType tokenType;

        try {
            tokenType = TokenType.valueOf(type.toUpperCase());
        }
        catch (IllegalArgumentException e){
            throw new RequestException(ExceptionMessage.TOKEN_TYPE_NOT_VALID);
        }
        token.setCreationDate(System.currentTimeMillis());
        token.setAccessToken(UUID.randomUUID().toString());
        token.setTokenType(tokenType);

        return tokenMapper.entityToDto(tokenRepository.save(token));
    }

    public void deleteById(Long id) {
        Token token = tokenRepository.findById(id)
                .orElseThrow(() -> new RequestException(ExceptionMessage.TOKEN_NOT_FOUND));

        if(token.getTokenType() == TokenType.ADMIN){
            List<Token> adminTokens = findByTokenType(TokenType.ADMIN);

            if(adminTokens.size() == 1) throw new LastTokenException(ExceptionMessage.TOKEN_ADMIN_LAST);
        }

        tokenRepository.deleteById(id);
    }

    public List<TokenResponseDTO> getAll() {
        return tokenRepository.findAll()
                .stream()
                .map(tokenMapper::entityToDto)
                .toList();
    }

    public List<Token> findAll() {
        return tokenRepository.findAll();
    }
}
