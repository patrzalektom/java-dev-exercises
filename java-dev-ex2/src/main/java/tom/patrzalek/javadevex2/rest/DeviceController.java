package tom.patrzalek.javadevex2.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tom.patrzalek.javadevex2.model.dto.DeviceDTO;
import tom.patrzalek.javadevex2.service.DeviceService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/device")
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    public List<DeviceDTO> getAll() {
        log.info("REST request to get all Device entites");
        return deviceService.findAll();
    }

    @GetMapping("/{deviceId}")
    public DeviceDTO getOne(@PathVariable Long deviceId) {
        log.info("REST request to get one Device entity by ID : {}", deviceId);
        return deviceService.findOneAsDTO(deviceId);
    }

    @PostMapping
    public DeviceDTO save(@RequestBody DeviceDTO deviceDTO) {
        log.info("REST request to save Device entity : {}", deviceDTO);
        return deviceService.save(deviceDTO);
    }

    @DeleteMapping
    public void deleteById(Long deviceId) {
        log.info("REST request to delete Device entity by ID : {}", deviceId);
        deviceService.deleteById(deviceId);
    }

}
