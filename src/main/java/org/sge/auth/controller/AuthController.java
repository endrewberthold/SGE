package org.sge.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sge.auth.dto.AuthResponseDTO;
import org.sge.auth.dto.LoginRequestDTO;
import org.sge.auth.dto.RegisterRequestDTO;
import org.sge.auth.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Authentication",
        description = "Endpoints for user registration, authentication, and session management."
)
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account in the system with the provided credentials"
    )
    @PostMapping("/register")
    public void register(@RequestBody RegisterRequestDTO dto){
        authService.register(dto);
    }

    @Operation(
            summary = "User login",
            description = "Authenticates a user with their credentials and returns an access token"
    )
    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO dto){
        return authService.login(dto);
    }
}
