package br.com.sensedia.users.adapters;

import br.com.sensedia.users.domains.User;
import br.com.sensedia.users.dtos.UserDto;
import br.com.sensedia.users.application.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class HttpUserController {

    private UserService userService;

    @Autowired //Spring 1
    public HttpUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    UserDto postUsers(@Validated @RequestBody UserDto newUserDto) {
        //DTO to Domain
        User newUser = new User(newUserDto.getFirstName(), newUserDto.getLastName(), newUserDto.getBirthDate(), newUserDto.getEmail());
        //Service
        User userCreated = this.userService.saveUsers(newUser);
        //Domain to DTO
        UserDto userCreatedDto = new UserDto(
                userCreated.getId(),
                userCreated.getFirstName(),
                userCreated.getLastName(),
                userCreated.getBirthDate(),
                userCreated.getEmail()
        );
        return userCreatedDto;
    }

    @GetMapping(path = "/users/{id}")
    UserDto getUser(@PathVariable String id) {
        //Service
        User userFound = userService.findUserById(id);
        //Domain to DTO
        UserDto userCreatedDto = new UserDto(
                userFound.getId(),
                userFound.getFirstName(),
                userFound.getLastName(),
                userFound.getBirthDate(),
                userFound.getEmail()
        );
        return userCreatedDto;
    }

    @GetMapping("/users")
    List<UserDto> getUsers(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        if (Objects.isNull(firstName) && Objects.isNull(lastName)) {
            //Service
            List<User> usersFound = userService.findAllUsers();
            //Domain to DTO
            List<UserDto> usersDto = usersFound.stream().map(userFound -> {
                UserDto userDto = new UserDto(userFound.getId(),
                        userFound.getFirstName(),
                        userFound.getLastName(),
                        userFound.getBirthDate(),
                        userFound.getEmail()
                );
                return userDto;
            }).collect(Collectors.toList());
            return usersDto;
        } else {
            //Service
            List<User> usersFound = userService.findAllUsers(firstName, lastName);
            //Domain to DTO
            List<UserDto> usersDto = usersFound.stream().map(userFound -> {
                UserDto userDto = new UserDto(userFound.getId(),
                        userFound.getFirstName(),
                        userFound.getLastName(),
                        userFound.getBirthDate(),
                        userFound.getEmail()
                );
                return userDto;
            }).collect(Collectors.toList());
            return usersDto;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable String id) {
        //Service
        userService.deleteUserById(id);
    }

    @PutMapping("/users/{id}")
    UserDto putUser(@Validated @RequestBody UserDto newUserDto, @PathVariable String id) {
        //DTO to Domain
        User newUser = new User(id, newUserDto.getFirstName(), newUserDto.getLastName(), newUserDto.getBirthDate(), newUserDto.getEmail());
        //Service
        User userUpdated = this.userService.updateUser(newUser);
        //Domain to DTO
        UserDto userUpdatedDto = new UserDto(
                userUpdated.getId(),
                userUpdated.getFirstName(),
                userUpdated.getLastName(),
                userUpdated.getBirthDate(),
                userUpdated.getEmail()
        );
        return userUpdatedDto;
    }

    @PatchMapping("/users/{id}")
    UserDto patchUser(@Validated @RequestBody UserDto partialUserDto, @PathVariable String id) {
        //DTO to Domain
        User partialUser = new User(id, partialUserDto.getFirstName(), partialUserDto.getLastName(), partialUserDto.getBirthDate(), partialUserDto.getEmail());
        //Service
        User userUpdated = this.userService.partialUpdateUserById(partialUser);
        //Domain to DTO
        UserDto userUpdatedDto = new UserDto(
                userUpdated.getId(),
                userUpdated.getFirstName(),
                userUpdated.getLastName(),
                userUpdated.getBirthDate(),
                userUpdated.getEmail()
        );
        return userUpdatedDto;
    }

}
