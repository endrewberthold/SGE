package org.sge.parking.service;

import org.sge.parking.dtos.ParkingRateRequestDTO;
import org.sge.parking.dtos.ParkingRateResponseDTO;
import org.sge.parking.entity.ParkingRate;
import org.sge.parking.repository.ParkingRateRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingRateService {
    private final ParkingRateRepository parkingRateRepository;

    public ParkingRateService(ParkingRateRepository parkingRateRepository) {
        this.parkingRateRepository = parkingRateRepository;
    }

    public ParkingRateResponseDTO create(
            ParkingRateRequestDTO dto
    ){
        ParkingRate rate = new ParkingRate();

        rate.setType(dto.type());
        rate.setAmount(dto.amount());
        rate.setActive(dto.active());

        ParkingRate saved = parkingRateRepository.save(rate);

        return new ParkingRateResponseDTO(
                saved.getId(),
                saved.getType(),
                saved.getAmount(),
                saved.getActive()
        );
    }
}
