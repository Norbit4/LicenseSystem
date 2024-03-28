package pl.norbit.backend.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.norbit.backend.dto.token.TokenResponseDTO;
import pl.norbit.backend.model.token.Token;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static pl.norbit.backend.helper.TokenHelper.createDefaultToken;

@SpringBootTest
class TokenMapperTest {

    @Autowired
    private TokenMapper underTest;

    @Test
    @DisplayName("Should map Token entity to TokenResponseDTO")
    void should_map_entity_to_dto() {

        //given
        Token token = createDefaultToken();

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(
                token.getId(),
                token.getAccessToken(),
                token.getCreationDate(),
                token.getTokenType());

        //when
        TokenResponseDTO actualResponseDTO  = underTest.entityToDto(token);

        //then
        assertEquals(actualResponseDTO, tokenResponseDTO);
    }

    @Test
    @DisplayName("Should return null when entity is null")
    void entityToDto_should_return_null_when_entity_is_null() {
        //given
        //when
        TokenResponseDTO actualResponseDTO = underTest.entityToDto(null);

        //then
        assertNull(actualResponseDTO);
    }
}