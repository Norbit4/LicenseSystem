package pl.norbit.backend.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.norbit.backend.dto.token.TokenResponseDTO;
import pl.norbit.backend.model.token.TokenType;
import pl.norbit.backend.service.TokenService;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null) {
            Optional<TokenResponseDTO> tokenEntity = tokenService.findByToken(token);

            if (tokenEntity.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }
            TokenType tokenType = tokenEntity.get().tokenType();

            Authentication auth = new UsernamePasswordAuthenticationToken(tokenType, null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + tokenType.name())));

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
