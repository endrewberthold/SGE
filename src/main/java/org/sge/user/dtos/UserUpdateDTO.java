package org.sge.user.dtos;

public record UserUpdateDTO(
        String email,
        String password
) {
}
