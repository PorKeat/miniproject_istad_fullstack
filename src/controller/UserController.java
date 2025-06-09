package controller;

import model.dto.CreateUserDto;
import model.dto.UserLoginDto;
import model.dto.UserResponseDto;
import model.repository.UserRepositoryImpl;
import model.services.UserServicesImp;

public class UserController {
    private final UserServicesImp userService = new UserServicesImp();
    public UserResponseDto createUser(CreateUserDto createUserDto) {
        return userService.createUser(createUserDto);
    }
    public UserResponseDto userLogin (UserLoginDto userLoginDto) {
        return userService.userLogin(userLoginDto);
    }
}
