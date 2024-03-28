package pl.norbit.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.norbit.backend.dto.license.CreatedLicenseDTO;
import pl.norbit.backend.dto.license.LicenseResponseDTO;
import pl.norbit.backend.dto.license.LicenseServerKeyDTO;
import pl.norbit.backend.exception.ExceptionMessage;
import pl.norbit.backend.exception.model.LicenseNotFoundException;
import pl.norbit.backend.exception.model.NotValidLicenseException;
import pl.norbit.backend.exception.model.RequestException;
import pl.norbit.backend.mapper.LicenseMapper;
import pl.norbit.backend.model.license.License;
import pl.norbit.backend.model.license.LicenseType;
import pl.norbit.backend.repository.LicenseRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final LicenseMapper licenseMapper;

    public LicenseResponseDTO save(CreatedLicenseDTO licenseDTO) {
        if(licenseDTO.owner() == null) throw new RequestException(ExceptionMessage.LICENSE_OWNER_NULL);

        License license = new License();

        license.setOwner(licenseDTO.owner());
        license.setDescription(licenseDTO.description());

        int daysToExpire = licenseDTO.expireDays();
        long now = System.currentTimeMillis();

        if(daysToExpire != 0){
            license.setExpirationDate(now + 1000L * 60 * 60 * 24 * daysToExpire);
            license.setLicenseType(LicenseType.DEFAULT);
        }else{
            license.setLicenseType(LicenseType.LIFETIME);
        }

        license.setCreationDate(now);
        license.setLicenseKey(UUID.randomUUID().toString());

        return licenseMapper.entityToDto(licenseRepository.save(license));
    }

    public LicenseServerKeyDTO generateServerKey(String key){
        License licenseEntity = licenseRepository.findByLicenseKey(key)
                .orElseThrow(() -> new LicenseNotFoundException(ExceptionMessage.LICENSE_NOT_FOUND));

        String newServerKey = UUID.randomUUID().toString();

        licenseEntity.setLastActive(System.currentTimeMillis());
        licenseEntity.setServerKey(newServerKey);

        return licenseMapper.entityToServerKeyDto(licenseRepository.save(licenseEntity));
    }

    public void isValidServerKey(LicenseServerKeyDTO licenseDTO){
        String serverKey = licenseDTO.serverKey();
        String licenseKey = licenseDTO.licenseKey();

        if(serverKey == null) throw new RequestException(ExceptionMessage.LICENSE_SERVER_KEY_NULL);
        if(licenseKey == null) throw new RequestException(ExceptionMessage.LICENSE_KEY_NULL);

        License licenseEntity =  licenseRepository.findByLicenseKey(licenseKey)
                .orElseThrow(() -> new LicenseNotFoundException(ExceptionMessage.LICENSE_NOT_FOUND));

        if(licenseEntity == null) throw new NotValidLicenseException(ExceptionMessage.LICENSE_NOT_FOUND);
        String licenseEntityServerKey = licenseEntity.getServerKey();

        if(!licenseEntityServerKey.equals(serverKey))
            throw new NotValidLicenseException((ExceptionMessage.LICENSE_WRONG_SERVER_KEY));
    }

    public void isValidKey(String key){
        License license = licenseRepository.findByLicenseKey(key)
                .orElseThrow(() -> new LicenseNotFoundException(ExceptionMessage.LICENSE_NOT_FOUND));

        if(isExpired(license)) throw new NotValidLicenseException(ExceptionMessage.LICENSE_IS_EXPIRED);
    }

    private boolean isExpired(License license){
        if(license.getLicenseType() == LicenseType.LIFETIME) return false;

        return license.getExpirationDate() < System.currentTimeMillis();
    }

    public void deleteById(Long id) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new RequestException(ExceptionMessage.LICENSE_NOT_FOUND));

        licenseRepository.delete(license);
    }

    public List<LicenseResponseDTO> getAll() {
        return licenseRepository.findAll()
                .stream()
                .map(licenseMapper::entityToDto)
                .toList();
    }
    public List<License> findAll() {
        return licenseRepository.findAll();
    }

    public void deleteAll() {
        licenseRepository.deleteAll();
    }
}