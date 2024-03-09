package pl.norbit.backend.dto.token;

import lombok.Builder;
import pl.norbit.backend.model.token.TokenType;

@Builder
public record TokenResponseDTO(Long id,
                               String accessToken,
                               long creationDate,
                               TokenType tokenType) {
}
