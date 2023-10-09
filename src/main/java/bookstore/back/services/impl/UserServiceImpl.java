package bookstore.back.services.impl;

import bookstore.back.entities.UserEntity;
import bookstore.back.exception.NotFoundException;
import bookstore.back.io.user.UserCreateRequest;
import bookstore.back.io.user.UserMapper;
import bookstore.back.io.user.UserResponseRequest;
import bookstore.back.io.user.UserUpdateRequest;
import bookstore.back.repositories.RentalRepository;
import bookstore.back.repositories.UserRepository;
import bookstore.back.services.UserService;
import bookstore.back.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidation userValidation;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void create(UserCreateRequest request) {
        UserEntity entity = new UserEntity();
        entity.setName(request.getName().trim());
        entity.setCity(request.getCity().trim());
        entity.setEmail(request.getEmail());
        entity.setAddress(request.getAddress().trim());
        entity.setNumberOfRentals(0);
        userValidation.validateForCreate(entity);
        userRepository.save(entity);
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário", id));
    }

    @Override
    public List<UserResponseRequest> getAll() {
        List<UserResponseRequest> usersWithNumberOfRentals = getUsersWithNumberOfRentals();

        return usersWithNumberOfRentals;
    }

    @Override
    public void update(UserUpdateRequest request) {
        UserEntity entity = findById(request.getId());
        entity.setName(request.getName().trim());
        entity.setCity(request.getCity().trim());
        entity.setEmail(request.getEmail());
        entity.setAddress(request.getAddress().trim());
        userValidation.validateUpdate(entity);
        userRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        UserEntity entity = findById(id);
        userValidation.validateForDelete(id);
        userRepository.delete(entity);
    }

    public List<UserResponseRequest> getUsersWithNumberOfRentals() {
        List<UserEntity> users = userRepository.findAll();
        List<UserResponseRequest> usersWithNumberOfRentals = new ArrayList<>();

        for (UserEntity user : users) {
            Integer numberOfRentals = rentalRepository.findAllByUserIdAndReturnDateIsNull(user.getId()).size();
            UserResponseRequest response = getUserResponseRequest(user, numberOfRentals);
            usersWithNumberOfRentals.add(response);
        }
        return usersWithNumberOfRentals;
    }

    private static UserResponseRequest getUserResponseRequest(UserEntity user, Integer numberOfRentals) {
        UserResponseRequest response = new UserResponseRequest();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setCity(user.getCity());
        response.setEmail(user.getEmail());
        response.setAddress(user.getAddress());
        response.setNumberOfRentals(numberOfRentals);
        return response;

    }

}
