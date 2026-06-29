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
import java.util.List;
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

    private ParkingSessionResponseDTO toDTO(ParkingSession session){
        return new ParkingSessionResponseDTO(
                session.getId(),
                session.getVehicle().getPlate(),
                session.getEntryTime(),
                session.getExitTime(),
                session.getStatus(),
                session.getTotalAmount()
        );
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
        session.setStatus(SessionStatus.OPEN);

        ParkingSession saved = parkingSessionRepository
                        .save(session);

        return toDTO(saved);
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

        return toDTO(saved);
    }

    public ParkingSessionResponseDTO findById(Long id){
        ParkingSession session = parkingSessionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sessão não encontrada."));

        return toDTO(session);
    }

    public List<ParkingSessionResponseDTO> findOpenSessions(){
        return parkingSessionRepository.findByStatus(SessionStatus.OPEN).stream().map(this::toDTO).toList();
    }

    public List<ParkingSessionResponseDTO> findClosedSessions(){
        return parkingSessionRepository.findByStatus(SessionStatus.CLOSED).stream().map(this::toDTO).toList();
    }

    public List<ParkingSessionResponseDTO> findByPlate(String plate){
        Vehicle vehicle = vehicleRepository.findByPlate(plate).orElseThrow(() ->
                new ResourceNotFoundException("Veículo não encontrado."));
        return parkingSessionRepository.findByOrderByEntryTimeDesc(vehicle).stream().map(this::toDTO).toList();
    }

    public List<ParkingSessionResponseDTO> findByClient(Long clientId){
        return parkingSessionRepository.findByVehicleClientId(clientId).stream().map(this::toDTO).toList();
    }
}
