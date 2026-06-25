package org.sge.client.service;

import org.sge.client.dtos.ClientDetailsResponseDTO;
import org.sge.client.dtos.ClientRequestDTO;
import org.sge.client.dtos.ClientResponseDTO;
import org.sge.client.dtos.UpdateClientDTO;
import org.sge.vehicle.dtos.VehicleResponseDTO;
import org.sge.client.entity.Client;
import org.sge.user.entity.User;
import org.sge.exception.BusinessException;
import org.sge.exception.ResourceNotFoundException;
import org.sge.client.repository.ClientRepository;
import org.sge.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public ClientService(
            ClientRepository clientRepository, UserRepository userRepository
    ){
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    private ClientResponseDTO toDTO(Client client){
        return new ClientResponseDTO(
                client.getId(),
                client.getName()
        );
    }

    private ClientDetailsResponseDTO toDetailsDTO(
            Client client
    ){
        List<VehicleResponseDTO> vehicles =
                client.getVehicles()
                        .stream()
                        .map(vehicle -> new VehicleResponseDTO(
                                vehicle.getId(),
                                vehicle.getMark(),
                                vehicle.getModel(),
                                vehicle.getColor()
                        ))
                        .toList();

        return new ClientDetailsResponseDTO(
                client.getId(),
                client.getName(),
                client.getDocument(),
                client.getPhone(),
                vehicles
        );
    }

    public ClientResponseDTO create(ClientRequestDTO dto){

        Optional<Client> existingClient = clientRepository.findByDocument(dto.document());

        if(existingClient.isPresent()){
            throw new BusinessException("Cliente já cadastrado.");
        }

        Client newClient = new Client();

        newClient.setName(dto.name());
        newClient.setDocument(dto.document());
        newClient.setPhone(dto.phone());

        Client savedClient = clientRepository.save(newClient);

        return toDTO(savedClient);
    }

    public ClientDetailsResponseDTO me(){

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado"));
        return toDetailsDTO(
                user.getClient()
        );
    }

    public ClientResponseDTO update(
            Long id,
            UpdateClientDTO dto
    ){
        Client client = clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado."));

        client.setName(dto.name());
        client.setDocument(dto.document());
        client.setPhone(dto.phone());

        Client savedClient = clientRepository.save(client);

        return toDTO(savedClient);
    }

    /**
     * Endpoint somente para ADMINISTRADORES*/
    public ClientDetailsResponseDTO findById(Long id){
        Client client = clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado"));

        return toDetailsDTO(client);
    }

    /**
     * Endpoint somente para ADMINISTRADORES*/
    public List<ClientResponseDTO> findAll(){
        return clientRepository.findAll()
                .stream()
                .map(client -> new ClientResponseDTO(
                        client.getId(),
                        client.getName()
                ))
                .toList();
    }
}
