package org.sge.client.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sge.client.dtos.ClientDetailsResponseDTO;
import org.sge.client.dtos.ClientRequestDTO;
import org.sge.client.dtos.ClientResponseDTO;
import org.sge.client.dtos.UpdateClientDTO;
import org.sge.client.service.ClientService;
import org.springframework.http.ResponseEntity;
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
            summary = "Register a new client",
            description = "Creates a new client record in the system."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Client successfully registered."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid client data provided."
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict. Document or email already registered."
            )
    })
    @PostMapping
    public ClientResponseDTO create(
            @RequestBody ClientRequestDTO dto
    ){
        return clientService.create(dto);
    }

    @Operation(
            summary = "Update client details",
            description = "Updates the information of an active client by their unique ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Client details updated successfully."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client not found."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid update payload."
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> update(
            @PathVariable Long id,
            @RequestBody UpdateClientDTO dto,
            ClientService clientService){
        return ResponseEntity.ok(
                clientService.update(id, dto));
    }

    @Operation(
            summary = "Find client by ID",
            description = "Retrieves detailed information about a specific client, including their registered vehicles. Restricted to ADMIN or ATTENDANT roles."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Client details retrieved successfully."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden. User lacks the required administrative permissions."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client not found."
            )
    })
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
            summary = "Get current authenticated profile",
            description = "Retrieves the profile information of the currently logged-in client extracted from the security context (JWT token)."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Authenticated profile data retrieved successfully."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized. Invalid or missing authentication token."
            )
    })
    @GetMapping("/me")
    public ClientDetailsResponseDTO me() {
        return clientService.me();
    }

    @Operation(
            summary = "List all clients",
            description = "Retrieves a list of all registered clients in the system. Restricted to ADMIN or ATTENDANT roles."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of clients retrieved successfully."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden. User lacks the required administrative permissions."
            )
    })
    @GetMapping("/all")
    public List<ClientResponseDTO> findAll(){
        return clientService.findAll();
    }
}