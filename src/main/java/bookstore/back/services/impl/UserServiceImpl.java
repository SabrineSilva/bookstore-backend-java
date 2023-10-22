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

import java.util.List;
import java.util.stream.Collectors;


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
    public UserEntity findById(Integer id) {
        UserEntity user = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("o usuário", id));

        Integer numberOfRentals = rentalRepository.countByUserId(id);
        user.setNumberOfRentals(numberOfRentals);

        return user;
    }


    @Override
    public UserEntity findByDeletedId(Integer id) {
        return userRepository.findByIdAndIsDeletedTrue(id)
                .orElseThrow(() -> new NotFoundException("o usuário deletado", id));

    }

    @Override
    public List<UserResponseRequest> getAll() {
        return userRepository.findAllByIsDeletedFalse().stream().map(userMapper::toUserResponseRequest).collect(Collectors.toList());
    }

    @Override
    public List<UserResponseRequest> getAllDeleted() {
        return userRepository.findAllByIsDeletedTrue().stream().map(userMapper::toUserResponseRequest).collect(Collectors.toList());
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
    public void delete(Integer id) {
        UserEntity entity = findById(id);
        userValidation.validateForDelete(id);
        entity.setDeleted(true);
        userRepository.save(entity);
    }

    @Override
    public void permanentlyDelete(Integer id) {
        UserEntity entity = findByDeletedId(id);
        userRepository.delete(entity);
    }


}
