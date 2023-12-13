package pl.gr.veterinaryapp.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gr.veterinaryapp.mapper.VetAppUserMapper;
import pl.gr.veterinaryapp.model.dto.UserDto;
import pl.gr.veterinaryapp.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/users")
@RestController
public class UserController {

    private final UserService userService;
    private final VetAppUserMapper mapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return mapper.toUserDtos(userService.getAllUsers());
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto user) {
        return mapper.toUserDto(userService.createUser(user));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        return mapper.toUserDto(userService.getUser(id));
    }
}
