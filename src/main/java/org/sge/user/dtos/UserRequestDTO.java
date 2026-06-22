package org.sge.user.dtos;

import org.sge.enums.Role;

public record UserRequestDTO(
        String email,
        String password,
        Role role
) {
}
