package pl.gr.veterinaryapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gr.veterinaryapp.exception.IncorrectDataException;
import pl.gr.veterinaryapp.exception.ResourceNotFoundException;
import pl.gr.veterinaryapp.model.dto.VetUserDto;
import pl.gr.veterinaryapp.model.entity.Role;
import pl.gr.veterinaryapp.model.entity.VetAppUser;
import pl.gr.veterinaryapp.repository.UserRepository;
import pl.gr.veterinaryapp.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public List<VetAppUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public VetAppUser getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found."));
    }

    @Override
    @Transactional
    public VetAppUser createUser(VetUserDto user) {
        userRepository.findByUsername(user.getUsername())
                .ifPresent(u -> {
                    throw new IncorrectDataException("Username " + user.getUsername() + " exists.");
                });
        VetAppUser newVetAppUser = new VetAppUser();
        newVetAppUser.setUsername(user.getUsername());
        newVetAppUser.setPassword(encoder.encode(user.getPassword()));
        newVetAppUser.setRole(new Role(user.getRole()));
        return userRepository.save(newVetAppUser);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with ID " + id + " not found."));
        userRepository.delete(user);
    }
}
