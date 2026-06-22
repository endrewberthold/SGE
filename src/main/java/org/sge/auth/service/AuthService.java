package org.sge.auth.service;

import org.sge.auth.dto.AuthResponseDTO;
import org.sge.auth.dto.LoginRequestDTO;
import org.sge.auth.dto.RegisterRequestDTO;
import org.sge.client.entity.Client;
import org.sge.user.entity.User;
import org.sge.enums.AuthProvider;
import org.sge.enums.Role;
import org.sge.client.repository.ClientRepository;
import org.sge.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, ClientRepository clientRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequestDTO dto) {

        if(userRepository.findByEmail(dto.email()).isPresent()){
            throw new RuntimeException("E-mail já cadastrado.");
        }

        User user = new User();

        user.setEmail(dto.email());
        user.setPassword(
                passwordEncoder.encode(dto.password())
        );
        user.setRole(Role.CLIENT);
        user.setProvider(AuthProvider.LOCAL);

        user = userRepository.save(user);

        Client client = new Client();

        client.setName(dto.name());
        client.setDocument(dto.document());
        client.setPhone(dto.phone());
        client.setUser(user);

        clientRepository.save(client);
    }

    public AuthResponseDTO login(LoginRequestDTO dto)
    {
    User user = userRepository
            .findByEmail(dto.email())
            .orElseThrow(() ->
                    new RuntimeException("Usuário não encontrado."));

    boolean validPassword =
            passwordEncoder.matches(
                    dto.password(),
                    user.getPassword()
            );

    if(!validPassword){
        throw new RuntimeException("Senha inválida.");
    }

    String token = jwtService.generateToken(user);

    return new AuthResponseDTO(token);

    }
}
