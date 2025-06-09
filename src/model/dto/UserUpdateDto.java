package model.dto;

public record UserUpdateDto(
        String username,
        String password,
        String email
) {

}
