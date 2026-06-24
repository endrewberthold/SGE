package org.sge.vehicle.dtos;

public record UpdateVehicleDTO(
        String plate,
        String mark,
        String model,
        String color
) {
}
