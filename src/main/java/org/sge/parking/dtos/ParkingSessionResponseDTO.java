package org.sge.parking.dtos;

import org.sge.enums.SessionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ParkingSessionResponseDTO(
        Long id,
        String plate,
        LocalDateTime entryTime,
        LocalDateTime exitTime,
        SessionStatus status,
        BigDecimal totalAmount
) {
}
