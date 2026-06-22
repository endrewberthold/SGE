package org.sge.vehicle.repository;

import org.sge.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByPlate(String Plate);
}
