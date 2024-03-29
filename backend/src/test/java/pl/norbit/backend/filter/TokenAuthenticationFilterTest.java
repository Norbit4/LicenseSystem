package pl.norbit.backend.filter;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.norbit.backend.dto.token.TokenResponseDTO;
import pl.norbit.backend.model.token.Token;
import pl.norbit.backend.model.token.TokenType;
import pl.norbit.backend.service.TokenService;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pl.norbit.backend.helper.TokenHelper.createAdminToken;

@SpringBootTest
class TokenAuthenticationFilterTest {

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private TokenAuthenticationFilter underTest;

    @Mock
    private MockFilterChain filterChain;

    @Test
    @DisplayName("Should authenticate with valid token")
    void should_authenticate_with_valid_token() throws ServletException, IOException {
        //given
        Token token = createAdminToken();

        long id = token.getId();
        String accessToken = token.getAccessToken();
        TokenType tokenType = token.getTokenType();
        long creationDate = token.getCreationDate();

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(id, accessToken, creationDate, tokenType);
        when(tokenService.findByAccessToken(accessToken)).thenReturn(Optional.of(tokenResponseDTO));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", accessToken);
        MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        underTest.doFilterInternal(request, response, filterChain);

        //then
        assertEquals(tokenType, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Should not authenticate with invalid token")
    void should_not_authenticate_with_invalid_token() throws ServletException, IOException {
        //given
        String token = "invalidToken";
        when(tokenService.findByAccessToken(token)).thenReturn(Optional.empty());

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", token);
        MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        underTest.doFilterInternal(request, response, filterChain);

        //then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }
}