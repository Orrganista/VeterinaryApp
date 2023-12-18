package pl.gr.veterinaryapp.service;

import pl.gr.veterinaryapp.model.dto.UserDto;
import pl.gr.veterinaryapp.model.entity.VetAppUser;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUser(long id);

    UserDto createUser(UserDto user);

    void deleteUser(long id);
}
