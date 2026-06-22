package org.sge.client.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sge.client.dtos.ClientDetailsResponseDTO;
import org.sge.client.dtos.ClientRequestDTO;
import org.sge.client.dtos.ClientResponseDTO;
import org.sge.client.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(
        name = "Client session",
        description = "Operations related to client registration,profile management and consultation"
)
@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @Operation(
            summary = "Create a new client",
            description = "Registers a new client"
    )
    @PostMapping
    public ClientResponseDTO create(
            @RequestBody ClientRequestDTO dto
    ){
        return clientService.create(dto);
    }

    @Operation(
            summary = "Search a client by ID (ADMIN/ATTENDANT only)",
            description = "Returns detailed information about specific client, including associated vehicles"
    )
    @GetMapping("/{id}")
    public ClientDetailsResponseDTO findById(

            @Parameter(
                    description = "Unique identifier of the client",
                    example = "1"
            )
            @PathVariable Long id
    ){
        return clientService.findById(id);
    }

    @Operation(
            summary = "Get authenticated client profile",
            description = "Returns the profile information of the currently authenticated client based on the JWT token"
    )
    @GetMapping("/me")
    public ClientDetailsResponseDTO me() {
        return clientService.me();
    }

    @Operation(
            summary = "List all clients (ADMIN/ATTENDANT only)",
            description = "Returns a list of all registered clients"
    )
    @GetMapping("/all")
    public List<ClientResponseDTO> findAll(){
        return clientService.findAll();
    }
}
