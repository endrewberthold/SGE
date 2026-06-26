package org.sge.parking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sge.parking.dtos.ParkingRateRequestDTO;
import org.sge.parking.dtos.ParkingRateResponseDTO;
import org.sge.parking.dtos.ParkingRateUpdateDTO;
import org.sge.parking.service.ParkingRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Parking Rates",
        description = "Endpoints for managing parking fee structures and pricing tiers."
)
@RestController
@RequestMapping("/parking-rates")
public class ParkingRateController {

    private final ParkingRateService parkingRateService;

    public ParkingRateController(ParkingRateService parkingRateService) {
        this.parkingRateService = parkingRateService;
    }

    @Operation(
            summary = "Create a new parking rate",
            description = "Defines a new pricing rule or rate tier for the parking lot."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Parking rate successfully created."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data or overlapping pricing rules."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden. User does not have permission to manage rates."
            )
    })
    @PostMapping
    public ParkingRateResponseDTO create(
            @RequestBody ParkingRateRequestDTO dto
    ){
        return parkingRateService.create(dto);
    }

    @Operation(
            summary = "Update an existing parking rate",
            description = "Modifies the pricing details or duration rules of a specific parking rate by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Parking rate updated successfully."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Parking rate not found."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden. Lack of permissions."
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ParkingRateResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ParkingRateUpdateDTO dto
    ){
        return ResponseEntity.ok(parkingRateService.update(id, dto));
    }

    @Operation(
            summary = "Deactivate a parking rate",
            description = "Performs a logical deletion, disabling a specific pricing tier so it can no longer be applied to new parkings."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Parking rate successfully deactivated."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Parking rate not found."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden. Lack of permissions."
            )
    })
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable Long id
    ){
        parkingRateService.deactivate(id);

        return ResponseEntity.noContent().build();
    }
}
