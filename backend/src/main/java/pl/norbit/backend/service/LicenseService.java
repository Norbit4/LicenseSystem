package pl.norbit.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.norbit.backend.exception.ExceptionMessage;
import pl.norbit.backend.exception.model.NotValidLicenseException;
import pl.norbit.backend.exception.model.RequestException;
import pl.norbit.backend.model.license.License;
import pl.norbit.backend.model.license.LicenseType;
import pl.norbit.backend.repository.LicenseRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LicenseService {

    private final LicenseRepository licenseRepository;

    public License save(License license) {
        long now = System.currentTimeMillis();

        if(license.getOwner() == null) throw new RequestException(ExceptionMessage.LICENSE_OWNER_NULL);

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

        if(licenseKey == null) throw new RequestException(ExceptionMessage.LICENSE_KEY_NULL);
        if(serverKey == null) throw new RequestException(ExceptionMessage.LICENSE_SERVER_KEY_NULL);

        License licenseEntity = findByKey(licenseKey);

        if(licenseEntity == null) throw new NotValidLicenseException(ExceptionMessage.LICENSE_NOT_FOUND);

        licenseEntity.setServerKey(serverKey);
        licenseEntity.setLastActive(System.currentTimeMillis());

        licenseRepository.save(licenseEntity);
    }

    public void isValidServerKey(String key, String serverKey){
        License licenseEntity = findByKey(key);

        if(licenseEntity == null) throw new NotValidLicenseException(ExceptionMessage.LICENSE_NOT_FOUND);
        String licenseEntityServerKey = licenseEntity.getServerKey();

        if(!licenseEntityServerKey.equals(serverKey))
            throw new NotValidLicenseException((ExceptionMessage.LICENSE_WRONG_SERVER_KEY));
    }

    public void isValid(String key){
        License license = findByKey(key);

        if(license == null) throw new NotValidLicenseException(ExceptionMessage.LICENSE_NOT_FOUND);

        if(isExpired(license)) throw new NotValidLicenseException(ExceptionMessage.LICENSE_IS_EXPIRED);

        if(license.getLastActive() == 0) return;

//        if(isLastActive(license)) throw new NotValidLicenseException("License was last active less than 2 minutes ago");
    }

    private boolean isLastActive(License license){
        long time = 1000 * 60 * 2L;

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
                .orElseThrow(() -> new RequestException(ExceptionMessage.LICENSE_NOT_FOUND));

        licenseRepository.deleteById(id);
    }

    public List<License> findAll() {
        return licenseRepository.findAll();
    }

    public void deleteAll() {
        licenseRepository.deleteAll();
    }
}
