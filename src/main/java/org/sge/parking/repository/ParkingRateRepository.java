package org.sge.parking.repository;

import org.sge.parking.entity.ParkingRate;
import org.sge.enums.RateType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingRateRepository extends JpaRepository<ParkingRate, Long> {

    Optional<ParkingRate> findByTypeAndActiveTrue(
            RateType type
    );

    List<ParkingRate> findAllByActiveTrue();
}
