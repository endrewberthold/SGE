package org.sge.parking.service.result;

import org.sge.parking.entity.ParkingRate;

import java.math.BigDecimal;

public record ParkingCalculationResult(
        BigDecimal totalAmount,
        ParkingRate parkingRate
) {
}
