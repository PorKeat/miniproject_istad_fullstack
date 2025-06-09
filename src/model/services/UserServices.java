package model.services;

import model.dto.CreateUserDto;
import model.dto.UserLoginDto;
import model.dto.UserResponseDto;
import model.dto.UserUpdateDto;

public interface UserServices {
    UserResponseDto createUser (CreateUserDto createUserDto);
    UserResponseDto updateUser (UserUpdateDto userUpdateDto);
    UserResponseDto userLogin (UserLoginDto userLoginDto);
}
