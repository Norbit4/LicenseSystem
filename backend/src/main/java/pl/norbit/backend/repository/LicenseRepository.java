package pl.norbit.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.norbit.backend.model.License;

import java.util.Optional;

@Repository
public interface LicenseRepository extends MongoRepository<License, String> {
    Optional<License> findByKey(String key);
}
