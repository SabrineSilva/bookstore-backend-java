package bookstore.back.services.impl;

import bookstore.back.entities.UserEntity;
import bookstore.back.exception.BusinessException;
import bookstore.back.io.user.UserCreateRequest;
import bookstore.back.io.user.UserMapper;
import bookstore.back.io.user.UserResponseRequest;
import bookstore.back.io.user.UserUpdateRequest;
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
        userValidation.validate(entity);
        userRepository.save(entity);
    }

    @Override
    public UserEntity findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Não foi possível encontrar o usuário."));
    }

    @Override
    public List<UserResponseRequest> getAll() {
        return userRepository.findAll().stream().map(userMapper::toUserResponseRequest).collect(Collectors.toList());
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

}
