package tom.patrzalek.javadevex2.service.mapper;

import org.mapstruct.Mapper;
import tom.patrzalek.javadevex2.model.dto.DeviceDTO;
import tom.patrzalek.javadevex2.model.entity.Device;

@Mapper(componentModel = "spring")
public interface DeviceMapper extends EntityMapper<DeviceDTO, Device> {

    Device toEntity(DeviceDTO deviceDTO);

    DeviceDTO toDTO(Device device);

}
