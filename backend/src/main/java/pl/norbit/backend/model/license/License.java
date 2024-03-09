package pl.norbit.backend.model.license;

import jakarta.persistence.*;
import lombok.*;


@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "license_table")
@Entity
public class License{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String owner;
    private String licenseKey;
    private String description;

    private long creationDate;
    private long expirationDate;

    private long lastActive;

    @Enumerated(EnumType.STRING)
    private LicenseType licenseType;

    private String serverKey;
}
