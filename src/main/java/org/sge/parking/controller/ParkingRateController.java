package org.sge.parking.controller;

import org.sge.parking.dtos.ParkingRateRequestDTO;
import org.sge.parking.dtos.ParkingRateResponseDTO;
import org.sge.parking.service.ParkingRateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
