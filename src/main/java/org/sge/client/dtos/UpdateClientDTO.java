package org.sge.client.dtos;

public record UpdateClientDTO(
        String name,
        String document,
        String phone
) {
}
