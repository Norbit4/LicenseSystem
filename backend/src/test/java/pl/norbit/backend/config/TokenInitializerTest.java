package pl.norbit.backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.norbit.backend.model.token.Token;
import pl.norbit.backend.model.token.TokenType;
import pl.norbit.backend.repository.TokenRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static pl.norbit.backend.helper.TokenHelper.createAdminToken;

@SpringBootTest
class TokenInitializerTest {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private Dotenv dotenv;

    @InjectMocks
    private TokenInitializer underTest;

    @Test
    @DisplayName("Should create admin token when token not exist")
    void should_create_admin_token_when_token_not_exist() {

        String defaultAccessToken = "admin-secret-token";
        //given
        given(tokenRepository.findTokensByTokenType(TokenType.ADMIN)).willReturn(new ArrayList<>());
        given(dotenv.get("DEFAULT_ACCESS_TOKEN")).willReturn(defaultAccessToken);

        //then
        underTest.run();

        //when
        ArgumentCaptor<Token> tokenArgumentCaptor = ArgumentCaptor.forClass(Token.class);

        verify(tokenRepository, times(1)).save(tokenArgumentCaptor.capture());

        Token value = tokenArgumentCaptor.getValue();

        assertNotNull(value);
        assertEquals(TokenType.ADMIN, value.getTokenType());
        assertNotNull(value.getAccessToken());
        assertEquals(defaultAccessToken, value.getAccessToken());
    }

    @Test
    @DisplayName("Should not create admin token when token exist")
    void should_not_create_admin_token_when_token_exist() {
        //given
        Token token = createAdminToken();

        List<Token> tokens = List.of(token);

        given(tokenRepository.findTokensByTokenType(TokenType.ADMIN)).willReturn(tokens);

        //then
        underTest.run();

        //when
        verify(tokenRepository, times(0)).save(any());
    }
}