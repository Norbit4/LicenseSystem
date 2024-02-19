package pl.norbit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.norbit.backend.model.Token;
import pl.norbit.backend.model.TokenType;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findTokensByTokenType(TokenType tokenType);

    Optional<Token> findTokenByAccessToken(String accessToken);
}
