package pl.norbit.backend.mapper;

import org.springframework.stereotype.Component;
import pl.norbit.backend.dto.token.TokenResponseDTO;
import pl.norbit.backend.model.token.Token;

@Component
public class TokenMapper {

    public TokenResponseDTO entityToDto(Token entity){
        if (entity == null) {
            return null;
        }

        return TokenResponseDTO.builder()
                .id(entity.getId())
                .accessToken(entity.getAccessToken())
                .creationDate(entity.getCreationDate())
                .tokenType(entity.getTokenType())
                .build();
    }
}
