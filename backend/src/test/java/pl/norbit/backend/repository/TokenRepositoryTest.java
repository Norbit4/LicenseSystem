package pl.norbit.backend.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.norbit.backend.model.token.Token;
import pl.norbit.backend.model.token.TokenType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static pl.norbit.backend.helper.TokenHelper.createDefaultToken;

@SpringBootTest
class TokenRepositoryTest {

    @Autowired
    private TokenRepository underTest;

    @Test
    @DisplayName("Should find token by token type")
    void should_find_tokens_by_token_type() {
        //given
        Token token = createDefaultToken();
        underTest.save(token);

        //when
        List<Token> tokensEntity = underTest.findTokensByTokenType(token.getTokenType());

        //then
        assertFalse(tokensEntity.isEmpty());
    }

    @Test
    @DisplayName("Should find token by token key")
    void should_find_token_by_token_key() {
        //given
        Token token = createDefaultToken();
        underTest.save(token);

        //when
        Optional<Token> tokenEntity = underTest.findTokenByAccessToken(token.getAccessToken());

        //then
        assertTrue(tokenEntity.isPresent());
    }
}