package org.sge.user.service;

import org.sge.enums.AuthProvider;
import org.sge.enums.Role;
import org.sge.exception.BusinessException;
import org.sge.exception.ResourceNotFoundException;
import org.sge.user.dtos.UserRequestDTO;
import org.sge.user.dtos.UserResponseDTO;
import org.sge.user.dtos.UserUpdateDTO;
import org.sge.user.entity.User;
import org.sge.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UserResponseDTO toDTO(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }

    public UserResponseDTO create(UserRequestDTO dto){

        if(userRepository.findByEmail(dto.email()).isPresent()){
            throw new BusinessException("E-mail já cadastrado");
        }

        if(dto.role() == Role.CLIENT){
            throw new BusinessException("CLIENT deve ser criado pelo fluxo de registro.");
        }

        User newUser = new User();

        newUser.setEmail(dto.email());
        newUser.setPassword(passwordEncoder.encode(dto.password()));
        newUser.setRole(dto.role());
        newUser.setProvider(AuthProvider.LOCAL);

        User savedUser = userRepository.save(newUser);

        return toDTO(savedUser);
    }

    public UserResponseDTO update(
            Long id,
            UserUpdateDTO dto
    ){
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Usuário não encontrado."));

        Optional<User> existingUser = userRepository.findByEmail(dto.email());

        if(existingUser.isPresent() && !existingUser.get().getId().equals(id)){
            throw new BusinessException(
                    "Email já cadastrado."
            );
        }

        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));

        User savedUser = userRepository.save(user);

        return toDTO(savedUser);
    }

    public void deactivate(Long id){
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Usuário não encontrado."));

        if(!user.getActive()){
            throw new BusinessException("Usuário já está desativado.");
        }

        user.setActive(false);

        userRepository.save(user);
    }
}
