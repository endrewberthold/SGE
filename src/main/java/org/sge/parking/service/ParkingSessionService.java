package org.sge.parking.service;

import org.sge.parking.service.result.ParkingCalculationResult;
import org.sge.parking.dtos.ParkingEntryDTO;
import org.sge.parking.dtos.ParkingExitDTO;
import org.sge.parking.dtos.ParkingSessionResponseDTO;
import org.sge.parking.entity.ParkingSession;
import org.sge.vehicle.entity.Vehicle;
import org.sge.enums.SessionStatus;
import org.sge.exception.BusinessException;
import org.sge.exception.ResourceNotFoundException;
import org.sge.parking.repository.ParkingSessionRepository;
import org.sge.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ParkingSessionService {

    private final VehicleRepository vehicleRepository;
    private final ParkingSessionRepository parkingSessionRepository;
    private final ParkingFeeCalculatorService parkingFeeCalculatorService;

    public ParkingSessionService(
            VehicleRepository vehicleRepository,
            ParkingSessionRepository parkingSessionRepository,
            ParkingFeeCalculatorService parkingFeeCalculatorService
    ){
        this.vehicleRepository = vehicleRepository;
        this.parkingSessionRepository = parkingSessionRepository;
        this.parkingFeeCalculatorService = parkingFeeCalculatorService;
    }

    public ParkingSessionResponseDTO registerEntry(ParkingEntryDTO dto) {

        Vehicle vehicle = vehicleRepository.findByPlate(dto.plate()
                ).orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));

        Optional<ParkingSession> openSession = parkingSessionRepository
                        .findByVehicleAndStatus(
                                vehicle,
                                SessionStatus.OPEN
                        );

        if (openSession.isPresent()){
            throw new BusinessException("Veículo já está estacionado.");
        }

        ParkingSession session = new ParkingSession();

        session.setVehicle(vehicle);
        session.setEntryTime(LocalDateTime.now());
        session.setStatus(
                SessionStatus.OPEN
        );

        ParkingSession saved = parkingSessionRepository
                        .save(session);

        return new ParkingSessionResponseDTO(
                saved.getId(),
                saved.getVehicle().getPlate(),
                saved.getEntryTime(),
                saved.getExitTime(),
                saved.getStatus(),
                saved.getTotalAmount()
        );
        }


    public ParkingSessionResponseDTO registerExit(ParkingExitDTO dto){

        Vehicle vehicle = vehicleRepository.findByPlate(dto.plate())
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));

        ParkingSession session = parkingSessionRepository
                        .findByVehicleAndStatus(
                                vehicle,
                                SessionStatus.OPEN
                        ).orElseThrow(() -> new ResourceNotFoundException("Nenhuma sessão aberta"));

        session.setExitTime(LocalDateTime.now());

        ParkingCalculationResult result = parkingFeeCalculatorService.calculate(session);

        session.setTotalAmount(result.totalAmount());

        session.setParkingRate(result.parkingRate());

        session.setStatus(SessionStatus.CLOSED);

        ParkingSession saved = parkingSessionRepository
                        .save(session);

        return new ParkingSessionResponseDTO(
                saved.getId(),
                saved.getVehicle().getPlate(),
                saved.getEntryTime(),
                saved.getExitTime(),
                saved.getStatus(),
                saved.getTotalAmount()
        );
    }
}
