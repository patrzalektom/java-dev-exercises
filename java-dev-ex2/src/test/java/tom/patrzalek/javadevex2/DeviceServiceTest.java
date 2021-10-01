package tom.patrzalek.javadevex2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tom.patrzalek.javadevex2.model.dto.DeviceDTO;
import tom.patrzalek.javadevex2.model.entity.Device;
import tom.patrzalek.javadevex2.repository.DeviceRepository;
import tom.patrzalek.javadevex2.service.DeviceService;
import tom.patrzalek.javadevex2.service.mapper.DeviceMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {

    private Device testDeviceOne;
    private Device testDeviceTwo;
    private DeviceDTO testDeviceOneDTO;
    private DeviceDTO testDeviceTwoDTO;
    private List<Device> devices;
    private List<DeviceDTO> deviceDTOs;

    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private DeviceMapper deviceMapper;

    private DeviceService deviceService;


    @BeforeEach
    public void setup() {
        testDeviceOne = new Device();
        testDeviceOne.setId(1L);
        testDeviceOne.setName("Device One");
        testDeviceTwo = new Device();
        testDeviceTwo.setId(2L);
        testDeviceTwo.setName("Device Two");

        testDeviceOneDTO = new DeviceDTO();
        testDeviceOneDTO.setId(1L);
        testDeviceOneDTO.setName("Device One");
        testDeviceTwoDTO = new DeviceDTO();
        testDeviceTwoDTO.setId(2L);
        testDeviceTwoDTO.setName("Device Two");

        devices = Arrays.asList(testDeviceOne, testDeviceTwo);
        deviceDTOs = Arrays.asList(testDeviceOneDTO, testDeviceTwoDTO);
        deviceService = new DeviceService(deviceRepository, deviceMapper);
    }

    @Test
    public void shouldGetAllDevicesAsList() {
        Mockito.when(deviceRepository.findAll()).thenReturn(devices);
        Mockito.when(deviceMapper.toDTO(devices)).thenReturn(deviceDTOs);

        List<DeviceDTO> actualDevices = deviceService.findAll();

        assertThat(actualDevices).isNotNull();
        assertThat(actualDevices.isEmpty()).isEqualTo(false);
        assertThat(actualDevices.size()).isEqualTo(2);

        Mockito.verify(deviceRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void shouldGetDeviceDTOById() {
        Mockito.when(deviceRepository.findById(1L)).thenReturn(Optional.of(testDeviceOne));
        Mockito.when(deviceMapper.toDTO(Mockito.any(Device.class))).thenReturn(testDeviceOneDTO);

        DeviceDTO actualDeviceDTO = deviceService.findOneAsDTO(1L);

        assertThat(actualDeviceDTO).isNotNull();
        assertThat(actualDeviceDTO.getId()).isEqualTo(testDeviceOneDTO.getId());
        assertThat(actualDeviceDTO.getName()).isEqualTo(testDeviceOneDTO.getName());

        Mockito.verify(deviceRepository, Mockito.times(1)).findById(1L);

    }

    @Test
    public void shouldGetDeviceById() {
        Mockito.when(deviceRepository.findById(1L)).thenReturn(Optional.of(testDeviceOne));

        Device actualDevice = deviceService.findOneAsEntity(1L);

        assertThat(actualDevice).isNotNull();
        assertThat(actualDevice.getId()).isEqualTo(testDeviceOne.getId());
        assertThat(actualDevice.getName()).isEqualTo(testDeviceOne.getName());

        Mockito.verify(deviceRepository, Mockito.times(1)).findById(1L);

    }

    @Test
    public void shouldSaveDevice() {
        DeviceDTO newDeviceDTO = new DeviceDTO();
        newDeviceDTO.setId(1L);
        newDeviceDTO.setName("New device");

        Mockito.when(deviceService.save(Mockito.any(DeviceDTO.class))).thenReturn(newDeviceDTO);

        DeviceDTO savedDeviceDTO = deviceService.save(newDeviceDTO);

        assertThat(savedDeviceDTO).isNotNull();
        assertThat(savedDeviceDTO.getId()).isEqualTo(1L);
        assertThat(savedDeviceDTO.getName()).isEqualTo(newDeviceDTO.getName());
    }

    @Test
    public void shouldDeleteDevice() {
        deviceService.deleteById(testDeviceOne.getId());
        Mockito.verify(deviceRepository, Mockito.times(1)).deleteById(testDeviceOne.getId());
    }
}
