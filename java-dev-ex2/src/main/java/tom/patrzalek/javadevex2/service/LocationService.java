package tom.patrzalek.javadevex2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tom.patrzalek.javadevex2.model.dto.LocationDTO;
import tom.patrzalek.javadevex2.model.entity.Device;
import tom.patrzalek.javadevex2.model.entity.Location;
import tom.patrzalek.javadevex2.repository.LocationRepository;
import tom.patrzalek.javadevex2.service.mapper.LocationMapper;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

    private final Random randomGenerator = new Random();

    private final DeviceService deviceService;
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationDTO getCurrentLocation(Long deviceId) {
        log.info("Request to get current location for Device by ID : {}", deviceId);
        LocationDTO location = new LocationDTO();
        location.setId(randomGenerator.nextLong());
        location.setDeviceId(deviceId);
        location.setLongitude(randomGenerator.nextInt());
        location.setLatitude(randomGenerator.nextInt());
        location.setDate(LocalDateTime.now());
        location.setIsLast(true);
        return location;
    }

    public LocationDTO getLastLocation(Long deviceId) {
        log.info("Request to get last location for Device by ID : {}", deviceId);
        return locationMapper.toDTO(locationRepository.findByDeviceIdAndIsLastTrue(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("Device " + deviceId + " was not located yet.")));
    }

    @Transactional
    public LocationDTO saveCurrentLocation(Long deviceId, LocationDTO locationDTO) {
        log.info("Request to save current location for Device by ID : {}", deviceId);
        Device device = deviceService.findOneAsEntity(deviceId);
        refreshLocation(device);
        locationDTO.setDate(LocalDateTime.now());
        locationDTO.setLongitude(randomGenerator.nextInt());
        locationDTO.setLatitude(randomGenerator.nextInt());
        locationDTO.setIsLast(true);
        Location currentLocation = locationMapper.toEntity(locationDTO);
        currentLocation.setDevice(device);
        currentLocation = locationRepository.save(currentLocation);
        return locationMapper.toDTO(currentLocation);
    }

    public List<LocationDTO> getLocationHistory(Long deviceId) {
        log.info("Request to get location history for Device by ID : {}", deviceId);
        return locationMapper.toDTO(locationRepository.findAllByDeviceId(deviceId));
    }

    private void refreshLocation(Device device) {
        Optional<Location> lastLocation = locationRepository.findByDeviceIdAndIsLastTrue(device.getId());
        lastLocation.ifPresent(location -> location.setIsLast(false));
    }

}
