package tom.patrzalek.javadevex2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tom.patrzalek.javadevex2.model.dto.DeviceDTO;
import tom.patrzalek.javadevex2.model.entity.Device;
import tom.patrzalek.javadevex2.repository.DeviceRepository;
import tom.patrzalek.javadevex2.service.mapper.DeviceMapper;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    public List<DeviceDTO> findAll() {
        log.info("Request to find all Device entities");
        return deviceMapper.toDTO(deviceRepository.findAll());
    }

    public DeviceDTO save(DeviceDTO deviceDTO) {
        log.info("Request to save Device entity : {}", deviceDTO);
        Device savedDevice = deviceRepository.save(deviceMapper.toEntity(deviceDTO));
        return deviceMapper.toDTO(savedDevice);
    }

    public DeviceDTO findOneAsDTO(Long deviceId) {
        log.info("Request to find Device DTO by ID : {}", deviceId);
        return deviceMapper.toDTO(findOneAsEntity(deviceId));
    }

    public Device findOneAsEntity(Long deviceId) {
        log.info("Request to find Device entity by ID : {}", deviceId);
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("Device with id " + deviceId + " does not exist."));
    }

    public void deleteById(Long deviceId) {
        log.info("Request to delete Device entity by ID : {}", deviceId);
        deviceRepository.deleteById(deviceId);
    }

}
