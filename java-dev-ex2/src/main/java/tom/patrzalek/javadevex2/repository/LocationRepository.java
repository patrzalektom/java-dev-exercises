package tom.patrzalek.javadevex2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tom.patrzalek.javadevex2.model.entity.Location;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByDeviceIdAndIsLastTrue(Long deviceId);

    List<Location> findAllByDeviceId(Long deviceId);

}
