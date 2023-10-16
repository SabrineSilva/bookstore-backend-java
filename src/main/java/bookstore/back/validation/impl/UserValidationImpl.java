package bookstore.back.validation.impl;

import bookstore.back.entities.RentalEntity;
import bookstore.back.entities.UserEntity;
import bookstore.back.exception.BusinessException;
import bookstore.back.repositories.RentalRepository;
import bookstore.back.repositories.UserRepository;
import bookstore.back.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserValidationImpl implements UserValidation {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void validateForCreate(UserEntity user) {
        validationName(user.getName());
        validationCity(user.getCity());
        validationEmail(user.getEmail());
        validationAddress(user.getAddress());
    }

    @Override
    public void validateUpdate(UserEntity user) {
        validationName(user.getName());
        validationCity(user.getCity());
        validationEmailUpdate(user);
        validationAddress(user.getAddress());
    }


    private void validationCity(String city) {
        if (city == null || city.isEmpty()) {
            throw new BusinessException("'Cidade' não pode ser nulo.");
        }
        if (city.length() > 50) {
            throw new BusinessException("Tamanho excedido. O máximo é de 50 caracteres.");
        }
    }

    private void validationName(String name) {
        if (name == null || name.isEmpty()) {
            throw new BusinessException("'Nome' não pode ser nulo.");
        }
        if (name.length() > 50) {
            throw new BusinessException("Tamanho excedido. O máximo é de 50 caracteres.");
        }

    }

    private void validationEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new BusinessException("'E-mail' não pode ser nulo.");
        }

        if (isValidEmailAddress(email)) {
            throw new BusinessException("Endereço de e-mail inválido.");
        }

        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new BusinessException("Já existe um usuário cadastrado com o mesmo e-mail");
        }
    }

    private void validationEmailUpdate(UserEntity user) {
        String email = user.getEmail();
        if (email == null || email.isEmpty()) {
            throw new BusinessException("'E-mail' não pode ser nulo.");
        }

        if (isValidEmailAddress(email)) {
            throw new BusinessException("Endereço de e-mail inválido.");
        }

        UserEntity existingUser = userRepository.findUserByEmail(email).orElse(null);

        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            throw new BusinessException("Já existe um usuário cadastrado com o mesmo e-mail");
        }
    }

    private boolean isValidEmailAddress(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return !email.matches(regex);
    }

    private void validationAddress(String address) {
        if (address == null || address.isEmpty()) {
            throw new BusinessException("'Endereço' não pode ser nulo.");
        }
        if (address.length() > 50) {
            throw new BusinessException("Tamanho excedido. O máximo é de 50 caracteres.");
        }
    }

    @Override
    public void validateForDelete(Integer id) {
        validateRelationship(id);
    }

    private void validateRelationship(Integer id) {
        List<Optional<RentalEntity>> rentals = rentalRepository.findByUserId(id);

        if (!rentals.isEmpty()) {
            throw new BusinessException("Não é possível deletar, pois, há pelo menos um aluguel associado à esse usuário.");
        }
    }
}