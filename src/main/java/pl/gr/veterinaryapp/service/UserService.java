package pl.gr.veterinaryapp.service;

import pl.gr.veterinaryapp.model.dto.VetUserDto;
import pl.gr.veterinaryapp.model.entity.VetAppUser;

import java.util.List;

public interface UserService {

    List<VetAppUser> getAllUsers();

    VetAppUser getUserById(long id);

    VetAppUser createUser(VetUserDto user);

    void deleteUser(long id);
}
