package model.dto;

import lombok.Builder;

@Builder

public record CreateUserDto(
        String uuid,
        String username,
        String password,
        String email) {
}

