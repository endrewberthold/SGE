package org.sge.vehicle.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sge.vehicle.dtos.UpdateVehicleDTO;
import org.sge.vehicle.dtos.VehicleRequestDTO;
import org.sge.vehicle.dtos.VehicleResponseDTO;
import org.sge.vehicle.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Implementação futura:
 * O proprio cliente poderá cadastrar seus próprios
 * veículos no sistema
 * */

@Tag(
        name = "Vehicle",
        description = "Operations related to vehicle registration and management"
)
@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @Operation(
            summary = "Register a new vehicle",
            description = "Registers a vehicle and associates it with an existing client."
    )
    @PostMapping("/{clientId}")
    public VehicleResponseDTO create(
            @Parameter(
                    description = "Unique identifier of the client who owns the vehicle",
                    example = "1"
            )
            @PathVariable Long clientId,
            @RequestBody VehicleRequestDTO dto){

        return vehicleService.create(clientId, dto);
    }

    @Operation(
            summary = "Update a vehicle information",
            description = "Update a registry of a vehicle with an existing client."
    )
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> update(
            @Parameter(
                    description = "Unique identifier of the client who owns the vehicle",
                    example = "1"
            )
            @PathVariable Long id,
            @RequestBody UpdateVehicleDTO dto
            ){
        return ResponseEntity.ok(
                vehicleService.update(id, dto)
        );
    }

    @Operation(
            summary = "Delete a vehicle information",
            description = "Delete a registry of a vehicle with an existing client."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(
                    description = "Unique identifier of the client who owns the vehicle",
                    example = "1"
            )
            @PathVariable Long id
    ){
        vehicleService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
