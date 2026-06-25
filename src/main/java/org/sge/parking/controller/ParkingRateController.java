package org.sge.parking.controller;

import org.sge.parking.dtos.ParkingRateRequestDTO;
import org.sge.parking.dtos.ParkingRateResponseDTO;
import org.sge.parking.dtos.ParkingRateUpdateDTO;
import org.sge.parking.service.ParkingRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking-rates")
public class ParkingRateController {

    private final ParkingRateService parkingRateService;

    public ParkingRateController(ParkingRateService parkingRateService) {
        this.parkingRateService = parkingRateService;
    }

    @PostMapping
    public ParkingRateResponseDTO create(
            @RequestBody ParkingRateRequestDTO dto
    ){
        return parkingRateService.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingRateResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ParkingRateUpdateDTO dto
    ){
        return ResponseEntity.ok(parkingRateService.update(id, dto));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable Long id
    ){
        parkingRateService.deactivate(id);

        return ResponseEntity.noContent().build();
    }
}
