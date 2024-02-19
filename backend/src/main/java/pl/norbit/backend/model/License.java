package pl.norbit.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Transient;


@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "license_table")
@Entity
public class License{

    public interface Get {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Get.class)
    private Long id;

    @JsonView(Get.class)
    private String owner;
    @JsonView(Get.class)
    private String licenseKey;
    @JsonView(Get.class)
    private String description;
    @JsonView(Get.class)
    private long creationDate;
    @JsonView(Get.class)
    private long expirationDate;
    private long lastActive;

    @JsonView(Get.class)
    private LicenseType licenseType;

    @Transient
    private int daysToExpire;

    @Transient
    private String serverKey;
}
