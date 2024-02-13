package pl.norbit.backend.service;

import org.springframework.stereotype.Service;
import pl.norbit.backend.model.License;
import pl.norbit.backend.repository.LicenseRepository;

import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {
    private final LicenseRepository licenseRepository;

    public LicenseService(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public License save(License license) {
        long now = System.currentTimeMillis();

        license.setCreationDate(now);
        license.setExpirationDate(now + 1000L * 60 * 60 * 24 * license.getDaysToExpire());
        license.setKey(UUID.randomUUID().toString());

        return licenseRepository.save(license);
    }

    public void updateActive(String key){
        License license = findByKey(key);
        license.setLastActive(System.currentTimeMillis());
        licenseRepository.save(license);
    }

    public boolean canStart(String key){
        License license = findByKey(key);

        if(license == null) return false;

        if(isExpired(license)) return false;

        if(license.getLastActive() == 0) return true;

        long time = 1000 * 60 * 2;

        return System.currentTimeMillis() - license.getLastActive() > time;
    }

    private boolean isExpired(License license){
        return license.getExpirationDate() < System.currentTimeMillis();
    }

    public License findByKey(String key) {
        return licenseRepository.findByKey(key).orElse(null);
    }

    public void deleteById(String id) {
        licenseRepository.deleteById(id);
    }

    public List<License> findAll() {
        return licenseRepository.findAll();
    }

    public void deleteAll() {
        licenseRepository.deleteAll();
    }
}
