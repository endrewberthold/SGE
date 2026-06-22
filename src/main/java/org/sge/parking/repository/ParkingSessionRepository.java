package org.sge.parking.repository;

import org.sge.parking.entity.ParkingSession;
import org.sge.vehicle.entity.Vehicle;
import org.sge.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {

    boolean existsByVehicleAndExitTimeIsNull(Vehicle vehicle);

    Optional<ParkingSession> findByVehicleAndExitTimeIsNull(Vehicle vehicle);

    Optional<ParkingSession> findByVehicleAndStatus(
            Vehicle vehicle,
            SessionStatus status
    );
}
