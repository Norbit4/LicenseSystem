package pl.norbit.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.norbit.backend.model.Token;

import java.util.List;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

    List<Token> findTokensByTokenType(String tokenType);
}
