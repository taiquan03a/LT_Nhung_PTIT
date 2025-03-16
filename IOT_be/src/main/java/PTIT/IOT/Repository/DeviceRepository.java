package PTIT.IOT.Repository;

import PTIT.IOT.Model.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer>, JpaSpecificationExecutor<Device> {
    @Query("SELECT d FROM Device d ORDER BY d.time DESC limit 12")
    List<Device> findFirst12();

    @Query("SELECT d FROM Device d ORDER BY d.time DESC limit 1")
    Device findFirst();
    Page<Device> findAll(Pageable pageable);

    @Query("SELECT COUNT(*) FROM Device d WHERE d.random > 70 AND d.time BETWEEN :startOfDay AND :endOfDay")
    int countRandom(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}
