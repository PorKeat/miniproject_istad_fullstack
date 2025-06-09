package model.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;


@Builder
public record UserResponseDto(
        String uuid,
        String username,
        String email,
        Boolean isDeleted
) {
}
