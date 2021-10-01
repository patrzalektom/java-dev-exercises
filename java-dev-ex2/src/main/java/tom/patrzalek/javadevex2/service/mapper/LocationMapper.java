package tom.patrzalek.javadevex2.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tom.patrzalek.javadevex2.model.dto.LocationDTO;
import tom.patrzalek.javadevex2.model.entity.Location;

@Mapper(componentModel = "spring", uses = DeviceMapper.class)
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {

    Location toEntity(LocationDTO locationDTO);

    @Mapping(source = "location.device.id", target = "deviceId")
    LocationDTO toDTO(Location location);

}
