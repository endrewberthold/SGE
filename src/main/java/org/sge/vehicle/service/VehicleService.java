package org.sge.vehicle.service;

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

    public VehicleService(
            VehicleRepository vehicleRepository,
            ClientRepository clientRepository
    ){
        this.vehicleRepository = vehicleRepository;
        this.clientRepository = clientRepository;
    }

    public VehicleResponseDTO create(Long clientId, VehicleRequestDTO dto){

        if (vehicleRepository.findByPlate(dto.plate()).isPresent()){
            throw new BusinessException("Veículo já cadastrado.");
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ExpressionException("Cliente não encontrado"));

        Vehicle vehicle = new Vehicle();

        vehicle.setPlate(dto.plate());
        vehicle.setMark(dto.mark());
        vehicle.setModel(dto.model());
        vehicle.setColor(dto.color());
        vehicle.setClient(client);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return new VehicleResponseDTO(
                savedVehicle.getId(),
                savedVehicle.getMark(),
                savedVehicle.getModel(),
                savedVehicle.getColor()
        );
    }
}
