package tom.patrzalek.javadevex2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tom.patrzalek.javadevex2.model.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
}
