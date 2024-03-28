package pl.norbit.backend.helper;

import pl.norbit.backend.model.token.Token;
import pl.norbit.backend.model.token.TokenType;

import java.util.UUID;

public class TokenHelper {

    public static Token createAdminToken() {
        return Token.builder()
                .id(1L)
                .accessToken(UUID.randomUUID().toString())
                .creationDate(System.currentTimeMillis())
                .tokenType(TokenType.ADMIN)
                .build();
    }
    public static Token createDefaultToken() {
        return Token.builder()
                .id(1L)
                .accessToken(UUID.randomUUID().toString())
                .creationDate(System.currentTimeMillis())
                .tokenType(TokenType.DEFAULT)
                .build();
    }
}
