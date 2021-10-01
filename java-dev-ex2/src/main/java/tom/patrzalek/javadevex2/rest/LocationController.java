package tom.patrzalek.javadevex2.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tom.patrzalek.javadevex2.model.dto.LocationDTO;
import tom.patrzalek.javadevex2.service.LocationService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/location")
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/{deviceId}/current")
    public LocationDTO getCurrentLocation(@PathVariable Long deviceId) {
        log.info("REST request to get current location for Device by ID : {}", deviceId);
        return locationService.getCurrentLocation(deviceId);
    }

    @GetMapping("/{deviceId}/last")
    public LocationDTO getLastLocation(@PathVariable Long deviceId) {
        log.info("REST request to get last location for Device by ID : {}", deviceId);
        return locationService.getLastLocation(deviceId);
    }

    @GetMapping("/{deviceId}/history")
    public List<LocationDTO> getLocationHistory(@PathVariable Long deviceId) {
        log.info("REST request to get location history for Device by ID : {}", deviceId);
        return locationService.getLocationHistory(deviceId);
    }

    @PostMapping("/{deviceId}")
    public LocationDTO saveCurrentLocation(@PathVariable Long deviceId, @RequestBody LocationDTO locationDTO) {
        log.info("REST request to save current location for Device by ID : {}", deviceId);
        return locationService.saveCurrentLocation(deviceId, locationDTO);
    }
}
