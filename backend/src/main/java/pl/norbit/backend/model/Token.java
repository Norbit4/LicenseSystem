package pl.norbit.backend.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;


@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "token_table")
@Entity
public class Token {
    public interface TokenGet {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(TokenGet.class)
    private Long id;

    @JsonView(TokenGet.class)
    private String accessToken;

    @JsonView(TokenGet.class)
    private long creationDate;

    @JsonView(TokenGet.class)
    private TokenType tokenType;

    @Transient
    private String tokenRequest;
}
