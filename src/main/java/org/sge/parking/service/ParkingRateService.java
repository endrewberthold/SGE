package org.sge.parking.service;

import org.sge.exception.ResourceNotFoundException;
import org.sge.parking.dtos.ParkingRateRequestDTO;
import org.sge.parking.dtos.ParkingRateResponseDTO;
import org.sge.parking.dtos.ParkingRateUpdateDTO;
import org.sge.parking.entity.ParkingRate;
import org.sge.parking.repository.ParkingRateRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingRateService {
    private final ParkingRateRepository parkingRateRepository;

    public ParkingRateService(ParkingRateRepository parkingRateRepository) {
        this.parkingRateRepository = parkingRateRepository;
    }

    private ParkingRateResponseDTO toDTO(ParkingRate parkingRate){
        return new ParkingRateResponseDTO(
                parkingRate.getId(),
                parkingRate.getType(),
                parkingRate.getAmount(),
                parkingRate.getActive()
        );
    }

    public ParkingRateResponseDTO create(
            ParkingRateRequestDTO dto
    ){
        ParkingRate newRate = new ParkingRate();

        newRate.setType(dto.type());
        newRate.setAmount(dto.amount());
        newRate.setActive(dto.active());

        ParkingRate savedRate = parkingRateRepository.save(newRate);

        return toDTO(savedRate);
    }

    public ParkingRateResponseDTO update(
            Long id,
            ParkingRateUpdateDTO dto
    ){
        ParkingRate rate = parkingRateRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Tarifa não encontrada"));

        rate.setAmount(dto.amount());

        ParkingRate savedRate = parkingRateRepository.save(rate);

        return toDTO(savedRate);
    }

    public void deactivate(Long id){
        ParkingRate rate = parkingRateRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tarifa não encontrada"));

        rate.setActive(false);

        parkingRateRepository.save(rate);
    }
}
