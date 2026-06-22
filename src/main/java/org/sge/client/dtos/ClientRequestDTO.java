package org.sge.client.dtos;

public record ClientRequestDTO(
        String name,
        String email,
        String document,
        String phone
) {
}
