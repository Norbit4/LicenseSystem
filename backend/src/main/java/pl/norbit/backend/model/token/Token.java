package pl.norbit.backend.model.token;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessToken;

    private long creationDate;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
}
