package tom.patrzalek.javadevex2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tom.patrzalek.javadevex2.model.dto.DeviceDTO;
import tom.patrzalek.javadevex2.model.dto.LocationDTO;
import tom.patrzalek.javadevex2.model.entity.Device;
import tom.patrzalek.javadevex2.model.entity.Location;
import tom.patrzalek.javadevex2.repository.LocationRepository;
import tom.patrzalek.javadevex2.service.DeviceService;
import tom.patrzalek.javadevex2.service.LocationService;
import tom.patrzalek.javadevex2.service.mapper.LocationMapper;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    private LocationDTO locationDTO;
    private List<Location> locationHistory;
    private List<LocationDTO> locationHistoryDTO;
    private Location location;
    private Device device;
    private DeviceDTO deviceDTO;

    @Mock
    private LocationRepository locationRepository;
    @Mock
    private LocationMapper locationMapper;
    @Mock
    private DeviceService deviceService;

    private LocationService locationService;

    @BeforeEach
    public void setup() {
        device = new Device();
        device.setId(10L);
        device.setName("Test device");
        deviceDTO = new DeviceDTO();
        deviceDTO.setId(10L);
        deviceDTO.setName("Test device");
        location = new Location();
        location.setId(1L);
        location.setDate(LocalDateTime.now());
        location.setLatitude(123);
        location.setLongitude(321);
        location.setIsLast(true);
        location.setDevice(device);
        locationDTO = new LocationDTO();
        locationDTO.setId(1L);
        locationDTO.setDate(LocalDateTime.now());
        locationDTO.setLatitude(123);
        locationDTO.setLongitude(321);
        locationDTO.setIsLast(true);
        locationDTO.setDeviceId(10L);
        locationHistory = Collections.singletonList(location);
        locationHistoryDTO = Collections.singletonList(locationDTO);
        locationService = new LocationService(deviceService, locationRepository, locationMapper);
    }

    @Test
    public void shouldReturnCurrentLocationForDevice() {
        LocationDTO currentLocation = locationService.getCurrentLocation(10L);
        assertThat(currentLocation.getDeviceId()).isEqualTo(10L);
    }

    @Test
    public void shouldThrowExceptionWhenReturnLastLocationForDevice() {
        Throwable notFoundException = assertThrows(EntityNotFoundException.class, () -> locationService.getLastLocation(1L));
        assertThat("Device 1 was not located yet.").isEqualTo(notFoundException.getMessage());
    }

    @Test
    public void shouldReturnLastLocationForDevice() {
        Mockito.when(locationRepository.findByDeviceIdAndIsLastTrue(10L)).thenReturn(Optional.of(location));
        Mockito.when(locationMapper.toDTO(Mockito.any(Location.class))).thenReturn(locationDTO);

        LocationDTO lastLocation = locationService.getLastLocation(10L);

        assertThat(lastLocation.getDeviceId()).isEqualTo(locationDTO.getDeviceId());
        assertThat(lastLocation.getLatitude()).isEqualTo(locationDTO.getLatitude());
        assertThat(lastLocation.getLongitude()).isEqualTo(locationDTO.getLongitude());
        assertThat(lastLocation.getIsLast()).isEqualTo(true);
    }

    @Test
    public void shouldSaveCurrentLocationForDevice() {
        Mockito.when(deviceService.findOneAsEntity(10L)).thenReturn(device);
        Mockito.when(locationRepository.findByDeviceIdAndIsLastTrue(10L)).thenReturn(Optional.of(location));
        Mockito.when(locationMapper.toEntity(locationDTO)).thenReturn(location);
        Mockito.when(locationMapper.toDTO(location)).thenReturn(locationDTO);
        Mockito.when(locationRepository.save(location)).thenReturn(location);

        LocationDTO savedCurrentLocation = locationService.saveCurrentLocation(10L, locationDTO);

        assertThat(savedCurrentLocation.getDeviceId()).isEqualTo(device.getId());
    }

    @Test
    public void shouldReturnLocationHistoryForDevice() {
        Mockito.when(locationRepository.findAllByDeviceId(10L)).thenReturn(locationHistory);
        Mockito.when(locationMapper.toDTO(locationHistory)).thenReturn(locationHistoryDTO);

        List<LocationDTO> locationHistory = locationService.getLocationHistory(10L);

        assertThat(locationHistory.stream().map(LocationDTO::getDeviceId)).containsExactlyInAnyOrder(10L);
    }

}
