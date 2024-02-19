package pl.norbit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.norbit.backend.model.License;

import java.util.Optional;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {

    Optional<License> findByLicenseKey(String key);
}
