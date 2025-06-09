package model.dto;

import lombok.Builder;

@Builder
public record UserLoginDto(
        String email,
        String password
) {
}
