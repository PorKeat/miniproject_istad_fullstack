package model.services;

import mapper.UserMapper;
import model.dto.CreateUserDto;
import model.dto.UserLoginDto;
import model.dto.UserResponseDto;
import model.dto.UserUpdateDto;
import model.entities.User;
import model.repository.UserRepositoryImpl;
import utils.PassWordManager;

public class UserServicesImp implements UserServices {
    private final UserRepositoryImpl userRepository = new UserRepositoryImpl();
    @Override
    public UserResponseDto createUser(CreateUserDto createUserDto) {
        User user = UserMapper.fromCreateUserDtoToUser(createUserDto);
        return UserMapper.fromUserToUserResponseDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto updateUser(UserUpdateDto userUpdateDto) {
        return null;
    }

    @Override
    public UserResponseDto userLogin(UserLoginDto userLoginDto) {

//        String hashedPassword = PassWordManager.hashPassWord(userLoginDto.password());
        User user = userRepository.login(userLoginDto.email(), userLoginDto.password());
        if (user != null) {
            return UserMapper.fromUserToUserResponseDto(user);
        }
        return null;
    }
}
