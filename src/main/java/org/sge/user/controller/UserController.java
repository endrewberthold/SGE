package org.sge.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.sge.user.dtos.UserRequestDTO;
import org.sge.user.dtos.UserResponseDTO;
import org.sge.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "User created with success"
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid credentials"
        ),
        @ApiResponse(
                responseCode = "403",
                description = "User without permission"
        )
})
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Create an administrator user",
            description = "Create an user with ADMIN or ATTENDANT profile"
    )
    @PostMapping
    public UserResponseDTO create(
            @RequestBody UserRequestDTO dto
    ){
        return userService.create(dto);
    }
}
