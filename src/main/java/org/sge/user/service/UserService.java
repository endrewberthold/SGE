package org.sge.user.service;

import org.sge.enums.AuthProvider;
import org.sge.enums.Role;
import org.sge.exception.BusinessException;
import org.sge.user.dtos.UserRequestDTO;
import org.sge.user.dtos.UserResponseDTO;
import org.sge.user.entity.User;
import org.sge.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
}

public UserResponseDTO create(UserRequestDTO dto){

        if(userRepository.findByEmail(dto.email()).isPresent()){
            throw new BusinessException("E-mail já cadastrado");
        }

        if(dto.role() == Role.CLIENT){
            throw new BusinessException("CLIENT deve ser criado pelo fluxo de registro.");
        }

        User user = new User();

        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(dto.role());
        user.setProvider(AuthProvider.LOCAL);

        User savedUser = userRepository.save(user);

        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }
}
