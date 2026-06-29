package org.sge.parking.repository;

import org.sge.parking.entity.ParkingSession;
import org.sge.vehicle.entity.Vehicle;
import org.sge.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {

    boolean existsByVehicleAndStatus(
            Vehicle vehicle,
            SessionStatus status
    );

    Optional<ParkingSession> findByVehicleAndStatus(
            Vehicle vehicle,
            SessionStatus status
    );

    List<ParkingSession> findByStatus(SessionStatus status);

    List<ParkingSession> findByOrderByEntryTimeDesc(Vehicle Vehicle);

    List<ParkingSession> findByVehicleClientId(Long id);
}
