package pl.norbit.backend.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.norbit.backend.dto.token.TokenResponseDTO;
import pl.norbit.backend.exception.model.LastTokenException;
import pl.norbit.backend.exception.model.RequestException;
import pl.norbit.backend.mapper.TokenMapper;
import pl.norbit.backend.model.token.Token;
import pl.norbit.backend.model.token.TokenType;
import pl.norbit.backend.repository.TokenRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static pl.norbit.backend.helper.TokenHelper.createAdminToken;
import static pl.norbit.backend.helper.TokenHelper.createDefaultToken;

@SpringBootTest
class TokenServiceTest {

    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private TokenMapper tokenMapper;
    @InjectMocks
    private TokenService underTest;

    @Test
    @DisplayName("Should find token by type")
    void findByTokenType_should_find_token_by_type() {
        //given
        Token token = createDefaultToken();
        List<Token> tokens = List.of(token);

        given(tokenRepository.findTokensByTokenType(token.getTokenType())).willReturn(tokens);

        //when
        List<Token> byTokenType = underTest.findByTokenType(token.getTokenType());

        //then
        verify(tokenRepository, times(1)).findTokensByTokenType(token.getTokenType());

        assertThat(byTokenType).isNotNull();
        assertEquals(1, byTokenType.size());
        assertEquals(token, byTokenType.get(0));
    }
    @Test
    @DisplayName("Should return empty list")
    void findByTokenType_should_return_empty_list() {
        //given
        TokenType tokenType = TokenType.ADMIN;

        given(tokenRepository.findTokensByTokenType(tokenType)).willReturn(List.of());

        //when
        List<Token> byTokenType = underTest.findByTokenType(tokenType);

        //then
        verify(tokenRepository, times(1)).findTokensByTokenType(tokenType);

        assertThat(byTokenType).isNotNull();
        assertEquals(0, byTokenType.size());
    }

    @Test
    @DisplayName("Should find token by access token")
    void findByAccessToken_should_find_token() {
        //given
        Token token = createDefaultToken();
        String accessToken = token.getAccessToken();

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(
                token.getId(),
                token.getAccessToken(),
                token.getCreationDate(),
                token.getTokenType()
        );

        given(tokenRepository.findTokenByAccessToken(accessToken)).willReturn(Optional.of(token));
        given(tokenMapper.entityToDto(token)).willReturn(tokenResponseDTO);

        //when
        Optional<TokenResponseDTO> accessResponse = underTest.findByAccessToken(accessToken);

        //then
        verify(tokenRepository, times(1)).findTokenByAccessToken(accessToken);

        assertNotNull(accessResponse);
        assertTrue(accessResponse.isPresent());

        TokenResponseDTO tokenResponse = accessResponse.get();

        assertEquals(tokenResponseDTO, tokenResponse);
    }

    @Test
    @DisplayName("Should create new token")
    void create_should_create_new_token() {
        //given
        Token token = createDefaultToken();
        String tokenType = token.getTokenType().toString();

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(
                token.getId(),
                token.getAccessToken(),
                token.getCreationDate(),
                token.getTokenType()
        );

        given(tokenRepository.save(any(Token.class))).willReturn(token);
        given(tokenMapper.entityToDto(token)).willReturn(tokenResponseDTO);

        //when
        TokenResponseDTO created = underTest.create(tokenType);

        //then
        verify(tokenRepository, times(1)).save(any(Token.class));

        assertNotNull(created);
        assertEquals(tokenResponseDTO, created);
    }

    @Test
    @DisplayName("Should throw exception when token type is not valid")
    void create_should_throw_exception_when_token_type_is_not_valid() {
        //given
        String tokenType = "not_valid";

        //when
        //then
        assertThrows(RequestException.class, () -> underTest.create(tokenType));
    }

    @Test
    @DisplayName("Should delete token by id")
    void deleteById_should_delete_default_token_by_id() {
        //given
        Token token = createDefaultToken();

        given(tokenRepository.findById(token.getId())).willReturn(Optional.of(token));

        //when
        underTest.deleteById(token.getId());

        //then
        verify(tokenRepository, times(1)).findById(token.getId());
        verify(tokenRepository, times(1)).deleteById(token.getId());
    }
    @Test
    @DisplayName("Should delete admin token by id")
    void deleteById_should_delete_admin_token_by_id() {
        //given
        Token tokenToDelete = createAdminToken();
        Long tokenToDeleteId = tokenToDelete.getId();

        Token token = createAdminToken();

        List<Token> adminTokens = List.of(tokenToDelete, token);

        given(tokenRepository.findById(tokenToDelete.getId())).willReturn(Optional.of(tokenToDelete));
        given(tokenRepository.findTokensByTokenType(tokenToDelete.getTokenType())).willReturn(adminTokens);

        //when
        underTest.deleteById(tokenToDelete.getId());

        //then
        verify(tokenRepository, times(1)).findById(tokenToDeleteId);
        verify(tokenRepository, times(1)).findTokensByTokenType(TokenType.ADMIN);
        verify(tokenRepository, times(1)).deleteById(tokenToDeleteId);
    }
    @Test
    @DisplayName("Should throw exception when token type is admin and is last")
    void deleteById_should_throw_exception_when_token_type_is_admin_and_is_last() {
        //given
        Token token = createAdminToken();
        Long tokenId = token.getId();

        List<Token> adminTokens = List.of(token);

        given(tokenRepository.findById(tokenId)).willReturn(Optional.of(token));
        given(tokenRepository.findTokensByTokenType(token.getTokenType())).willReturn(adminTokens);

        //when
        //then
        assertThrows(LastTokenException.class, () -> underTest.deleteById(tokenId));
    }

    @Test
    @DisplayName("Should throw exception when token not found")
    void deleteById_should_throw_exception_when_token_not_found() {
        //given
        Long tokenId = 1L;

        given(tokenRepository.findById(tokenId)).willReturn(Optional.empty());

        //when
        //then
        assertThrows(RequestException.class, () -> underTest.deleteById(tokenId));
    }

    @Test
    @DisplayName("Should get all tokens")
    void getAll_should_get_all_tokens() {
        //given
        Token token = createDefaultToken();
        List<Token> tokens = List.of(token);

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(
                token.getId(),
                token.getAccessToken(),
                token.getCreationDate(),
                token.getTokenType()
        );

        given(tokenRepository.findAll()).willReturn(tokens);
        given(tokenMapper.entityToDto(token)).willReturn(tokenResponseDTO);

        //when
        List<TokenResponseDTO> all = underTest.getAll();

        //then
        verify(tokenRepository, times(1)).findAll();

        assertThat(all).isNotNull();
        assertEquals(1, all.size());
        assertEquals(tokenResponseDTO, all.get(0));
    }
}