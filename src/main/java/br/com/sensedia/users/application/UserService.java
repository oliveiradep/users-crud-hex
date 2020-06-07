package br.com.sensedia.users.application;

import br.com.sensedia.users.adapters.UserNotFoundException;
import br.com.sensedia.users.domains.User;
import br.com.sensedia.users.entities.UserEntity;
import br.com.sensedia.users.repositories.UserRepository;
import br.com.sensedia.users.adapters.UnprocessableEntityException;
import br.com.sensedia.users.repositories.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserRepositoryCustom userRepositoryCustom;

    @Autowired
    public UserService(UserRepository userRepository, UserRepositoryCustom userRepositoryCustom) {
        this.userRepository = userRepository;
        this.userRepositoryCustom = userRepositoryCustom;
    }

    public User saveUsers(User user) {
        //Domain to Entity
        UserEntity newUserEntity = new UserEntity(user.getFirstName(), user.getLastName(), user.getBirthDate(), user.getEmail());
        //Mongo
        UserEntity userCreatedEntity = this.userRepository.save(newUserEntity);
        //Entity to Domain
        User userCreated = new User(userCreatedEntity.getId(),
                userCreatedEntity.getFirstName(),
                userCreatedEntity.getLastName(),
                userCreatedEntity.getBirthDate(),
                userCreatedEntity.getEmail()
        );
        return userCreated;
    }

    public User findUserById(String id) {
        //Mongo
        UserEntity userFoundEntity = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        //Entity to Domain
        return new User(userFoundEntity.getId(),
                userFoundEntity.getFirstName(),
                userFoundEntity.getLastName(),
                userFoundEntity.getBirthDate(),
                userFoundEntity.getEmail()
        );
    }

    public List<User> findAllUsers() {
        //Mongo
        List<UserEntity> usersFoundEntity = this.userRepository.findAll();
        //Entity to Domain
        List<User> users = usersFoundEntity.stream().map(userFoundEntity -> {
            User user = new User(userFoundEntity.getId(),
                    userFoundEntity.getFirstName(),
                    userFoundEntity.getLastName(),
                    userFoundEntity.getBirthDate(),
                    userFoundEntity.getEmail()
            );
            return user;
        }).collect(Collectors.toList());
        return users;
    }

    public List<User> findAllUsers(String firstName, String lastName) {
        //Mongo
        List<UserEntity> usersFoundEntity = this.userRepositoryCustom.findAll(firstName, lastName);
        //Entity to Domain
        List<User> users = usersFoundEntity.stream().map(userFoundEntity -> {
            User user = new User(userFoundEntity.getId(),
                    userFoundEntity.getFirstName(),
                    userFoundEntity.getLastName(),
                    userFoundEntity.getBirthDate(),
                    userFoundEntity.getEmail()
            );
            return user;
        }).collect(Collectors.toList());
        return users;
    }

    public void deleteUserById(String id) {
        this.userRepository.findById(id)
                .map(userFoundEntity -> {
                    //Entity to Domain
                    User user = new User(userFoundEntity.getFirstName(), userFoundEntity.getLastName(), userFoundEntity.getBirthDate(), userFoundEntity.getEmail());

                    //Business rule
                    if (user.getAge() >= 30 || Objects.equals(user.getAge(),0)) {
                        //Mongo
                        this.userRepository.deleteById(id);
                    } else {
                        throw new UnprocessableEntityException("{\"message\" : \"Can't delete user " + id + ".\"}");
                    }

                    return null;
                }).orElseGet(() -> {
            return null;
        });
    }

    public User updateUser(User user) {
        //Domain to Entity
        UserEntity actualUserEntity = new UserEntity(user.getId(), user.getFirstName(), user.getLastName(), user.getBirthDate(), user.getEmail());
        //Mongo
        UserEntity userUpdatedEntity = this.userRepository.save(actualUserEntity);
        //Entity to Domain
        User userUpdated = new User(userUpdatedEntity.getId(),
                userUpdatedEntity.getFirstName(),
                userUpdatedEntity.getLastName(),
                userUpdatedEntity.getBirthDate(),
                userUpdatedEntity.getEmail()
        );
        return userUpdated;
    }

    public User partialUpdateUserById(User user) {
        //Domain to Entity
        UserEntity partialUserEntity = new UserEntity(user.getId(), user.getFirstName(), user.getLastName(), user.getBirthDate(), user.getEmail());
        //Mongo
        return this.userRepository.findById(user.getId())
                .map(userFoundEntity -> {
                    if (Objects.isNull(partialUserEntity.getFirstName())) {
                        partialUserEntity.setFirstName(userFoundEntity.getFirstName());
                    }
                    if (Objects.isNull(partialUserEntity.getLastName())) {
                        partialUserEntity.setLastName(userFoundEntity.getLastName());
                    }
                    if (Objects.isNull(partialUserEntity.getBirthDate())) {
                        partialUserEntity.setBirthDate(userFoundEntity.getBirthDate());
                    }
                    if (Objects.isNull(partialUserEntity.getEmail())) {
                        partialUserEntity.setEmail(userFoundEntity.getEmail());
                    }
                    //Mongo
                    UserEntity userUpdatedEntity = this.userRepository.save(partialUserEntity);
                    //Entity to Domain
                    User userUpdated = new User(userUpdatedEntity.getId(),
                            userUpdatedEntity.getFirstName(),
                            userUpdatedEntity.getLastName(),
                            userUpdatedEntity.getBirthDate(),
                            userUpdatedEntity.getEmail()
                    );
                    return userUpdated;
                }).orElseThrow(() -> new UserNotFoundException(user.getId()));
    }

}
