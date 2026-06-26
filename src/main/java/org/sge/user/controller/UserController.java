package org.sge.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sge.user.dtos.UserRequestDTO;
import org.sge.user.dtos.UserResponseDTO;
import org.sge.user.dtos.UserUpdateDTO;
import org.sge.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Users",
        description = "Endpoints for managing user accounts and profiles."
)
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Create an administrative user",
            description = "Creates a new user with administrative privileges (ADMIN or ATTENDANT role)."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data or credentials."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden. User does not have permission to perform this action.")
    })
    @PostMapping
    public UserResponseDTO create(
            @RequestBody UserRequestDTO dto
    ){
        return userService.create(dto);
    }

    @Operation(
            summary = "Update user details",
            description = "Updates the profile information of an existing user by their ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User updated successfully."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden. Lack of permissions."
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Long id,
            @RequestBody UserUpdateDTO dto
    ){
        return ResponseEntity.ok(
                userService.update(id, dto)
        );
    }

    @Operation(
            summary = "Deactivate a user account",
            description = "Performs a logical deletion by deactivating the user status without removing them from the database."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "User successfully deactivated."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden. Lack of permissions."
            )
    })
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable Long id
    ){
        userService.deactivate(id);

        return ResponseEntity.noContent().build();
    }
}
