package pl.norbit.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.norbit.backend.exception.NotValidLicenseException;
import pl.norbit.backend.exception.RequestException;
import pl.norbit.backend.model.License;
import pl.norbit.backend.model.LicenseType;
import pl.norbit.backend.repository.LicenseRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LicenseService {

    private final LicenseRepository licenseRepository;

    public License save(License license) {
        long now = System.currentTimeMillis();

        if(license.getOwner() == null) throw new RequestException("Owner cannot be null");

        int daysToExpire = license.getDaysToExpire();

        if(daysToExpire != 0){
            license.setExpirationDate(now + 1000L * 60 * 60 * 24 * daysToExpire);
            license.setLicenseType(LicenseType.DEFAULT);
        }else{
            license.setLicenseType(LicenseType.LIFETIME);
        }

        license.setCreationDate(now);
        license.setLicenseKey(UUID.randomUUID().toString());

        return licenseRepository.save(license);
    }

    public void updateActive(License license){
        String licenseKey = license.getLicenseKey();
        String serverKey = license.getServerKey();

        if(licenseKey == null) throw new RequestException("License key cannot be null");

        if(serverKey == null) throw new RequestException("Server key cannot be null");

        License repoLicense = findByKey(licenseKey);

        if(repoLicense == null) throw new NotValidLicenseException("License not found");

        repoLicense.setLastActive(System.currentTimeMillis());

        licenseRepository.save(repoLicense);
    }

    public void isValid(String key){
        License license = findByKey(key);

        if(license == null) throw new NotValidLicenseException("License not found");

        if(isExpired(license)) throw new NotValidLicenseException("License is expired");

        if(license.getLastActive() == 0) return;

        if(isLastActive(license)) throw new NotValidLicenseException("License was last active less than 2 minutes ago");
    }

    private boolean isLastActive(License license){
        long time = 1000 * 60 * 2;

        return license.getLastActive() + time > System.currentTimeMillis();
    }

    private boolean isExpired(License license){
        if(license.getLicenseType() == LicenseType.LIFETIME) return false;

        return license.getExpirationDate() < System.currentTimeMillis();
    }

    public License findByKey(String key) {
        return licenseRepository.findByLicenseKey(key).orElse(null);
    }

    public void deleteById(Long id) {
        licenseRepository.findById(id)
                .orElseThrow(() -> new RequestException("Token with id " + id + " does not exist!"));

        licenseRepository.deleteById(id);
    }

    public List<License> findAll() {
        return licenseRepository.findAll();
    }

    public void deleteAll() {
        licenseRepository.deleteAll();
    }
}
