package org.sge.vehicle.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data for vehivle registration")
public record VehicleRequestDTO(
        @Schema(
                description = "Vehicle plate",
                example = "ABC1D23"
        )
        String plate,
        @Schema(
                description = "Vehicle mark",
                example = "Honda"
        )
        String mark,
        @Schema(
                description = "Vehicle model",
                example = "City 2.0"
        )
        String model,
        @Schema(
                description = "Vehicle color",
                example = "White"
        )
        String color
) {
}
