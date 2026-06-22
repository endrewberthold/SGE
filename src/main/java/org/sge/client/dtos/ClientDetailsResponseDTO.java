package org.sge.client.dtos;

import org.sge.vehicle.dtos.VehicleResponseDTO;

import java.util.List;

public record ClientDetailsResponseDTO(
        Long id,
        String name,
        String document,
        String phone,
        List<VehicleResponseDTO> vehicles
) {
}
