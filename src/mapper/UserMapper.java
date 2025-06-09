package mapper;

import model.dto.CreateUserDto;
import model.dto.UserResponseDto;
import model.entities.User;

import java.util.Random;
import java.util.UUID;

public class UserMapper {
    public static User fromCreateUserDtoToUser(CreateUserDto createUserDto){
        return User.builder()
                .id(new Random().nextInt(99999))
                .uuid(UUID.randomUUID().toString())
                .username(createUserDto.username())
                .email(createUserDto.email())
                .password(createUserDto.password())
                .isDeleted(false)
                .build();
    }
    public static UserResponseDto fromUserToUserResponseDto(User user){
        return UserResponseDto
                .builder()
                .uuid(user.getUuid())
                .username(user.getUsername())
                .email(user.getEmail())
                .isDeleted(user.getIsDeleted())
                .build();
    }
}
