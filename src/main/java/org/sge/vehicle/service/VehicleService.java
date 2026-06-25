package org.sge.vehicle.service;

import org.sge.enums.SessionStatus;
import org.sge.exception.ResourceNotFoundException;
import org.sge.parking.repository.ParkingSessionRepository;
import org.sge.vehicle.dtos.UpdateVehicleDTO;
import org.sge.vehicle.dtos.VehicleRequestDTO;
import org.sge.vehicle.dtos.VehicleResponseDTO;
import org.sge.client.entity.Client;
import org.sge.vehicle.entity.Vehicle;
import org.sge.exception.BusinessException;
import org.sge.client.repository.ClientRepository;
import org.sge.vehicle.repository.VehicleRepository;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ClientRepository clientRepository;
    private final ParkingSessionRepository parkingSessionRepository;

    public VehicleService(
            VehicleRepository vehicleRepository,
            ClientRepository clientRepository, ParkingSessionRepository parkingSessionRepository
    ){
        this.vehicleRepository = vehicleRepository;
        this.clientRepository = clientRepository;
        this.parkingSessionRepository = parkingSessionRepository;
    }

    private VehicleResponseDTO toDTO(Vehicle vehicle){
        return new VehicleResponseDTO(
                vehicle.getId(),
                vehicle.getMark(),
                vehicle.getModel(),
                vehicle.getColor()
        );
    }

    public VehicleResponseDTO create(Long clientId, VehicleRequestDTO dto){

        if (vehicleRepository.findByPlate(dto.plate()).isPresent()){
            throw new BusinessException("Veículo já cadastrado.");
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ExpressionException("Cliente não encontrado"));

        Vehicle newVehicle = new Vehicle();

        newVehicle.setPlate(dto.plate());
        newVehicle.setMark(dto.mark());
        newVehicle.setModel(dto.model());
        newVehicle.setColor(dto.color());
        newVehicle.setClient(client);

        Vehicle savedVehicle = vehicleRepository.save(newVehicle);

        return toDTO(savedVehicle);
    }

    public VehicleResponseDTO update(
            Long id,
            UpdateVehicleDTO dto
    ){
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Veiculo não encontrado."));

        vehicleRepository.findByPlate(dto.plate())
                .orElseThrow(() ->
                        new BusinessException("Placa já cadastrada"));

        vehicle.setPlate(dto.plate());
        vehicle.setMark(dto.mark());
        vehicle.setModel(dto.model());
        vehicle.setColor(dto.color());

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return toDTO(savedVehicle);
    }

    public void delete(Long id){

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Veiculo não encontrado."));

        boolean hasOpenSession = parkingSessionRepository.existsByVehicleAndStatus(
                vehicle,
                SessionStatus.OPEN
        );

        if(hasOpenSession){
            throw new BusinessException(
                    "Veículo possui sessão aberta."
            );
        }

        vehicleRepository.delete(vehicle);
    }
}
