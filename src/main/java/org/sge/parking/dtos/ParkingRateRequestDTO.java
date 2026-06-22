package org.sge.parking.dtos;

import org.sge.enums.RateType;

import java.math.BigDecimal;

public record ParkingRateRequestDTO (
        RateType type,
        BigDecimal amount,
        Boolean active
){
}
