package org.sge.parking.dtos;

import java.math.BigDecimal;

public record ParkingRateUpdateDTO(
        BigDecimal amount
) {
}
