package bookstore.back.validation.impl;
import bookstore.back.entities.RentalEntity;
import bookstore.back.entities.UserEntity;
import bookstore.back.exception.BusinessException;
import bookstore.back.repositories.RentalRepository;
import bookstore.back.repositories.UserRepository;
import bookstore.back.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        List<String> errors = new ArrayList<>();

        validationName(user.getName(), errors);
        validationCity(user.getCity(), errors);
        validationEmail(user.getEmail(), errors);
        validationAddress(user.getAddress(), errors);

        if (!errors.isEmpty()) {
            throw new BusinessException(String.join(" ", errors));
        }
    }

    @Override
    public void validateUpdate(UserEntity user) {
        List<String> errors = new ArrayList<>();

        validationName(user.getName(), errors);
        validationCity(user.getCity(), errors);
        validationEmailUpdate(user, errors);
        validationAddress(user.getAddress(), errors);

        if (!errors.isEmpty()) {
            throw new BusinessException(String.join(" ", errors));
        }
    }

    private void validationCity(String city, List<String> errors) {
        if (city == null || city.isEmpty()) {
            errors.add("'Cidade' não pode ser nulo.");
        }
        if (city.length() > 50) {
            errors.add("Tamanho excedido. O máximo é de 50 caracteres.");
        }
    }

    private void validationName(String name, List<String> errors) {
        if (name == null || name.isEmpty()) {
            errors.add("'Nome' não pode ser nulo.");
        }
        if (name.length() > 50) {
            errors.add("Tamanho excedido. O máximo é de 50 caracteres.");
        }
    }

    private void validationEmail(String email, List<String> errors) {
        if (email == null || email.isEmpty()) {
            errors.add("'E-mail' não pode ser nulo.");
        }

        if (isValidEmailAddress(email)) {
            errors.add("Endereço de e-mail inválido.");
        }

        if (userRepository.findUserByEmail(email).isPresent()) {
            errors.add("Já existe um usuário cadastrado com o mesmo e-mail");
        }
    }

    private void validationEmailUpdate(UserEntity user, List<String> errors) {
        String email = user.getEmail();
        if (email == null || email.isEmpty()) {
            errors.add("'E-mail' não pode ser nulo.");
        }

        if (isValidEmailAddress(email)) {
            errors.add("Endereço de e-mail inválido.");
        }

        UserEntity existingUser = userRepository.findUserByEmail(email).orElse(null);

        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            errors.add("Já existe um usuário cadastrado com o mesmo e-mail");
        }
    }

    private boolean isValidEmailAddress(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return !email.matches(regex);
    }

    private void validationAddress(String address, List<String> errors) {
        if (address == null || address.isEmpty()) {
            errors.add("'Endereço' não pode ser nulo.");
        }
        if (address.length() > 50) {
            errors.add("Tamanho excedido. O máximo é de 50 caracteres.");
        }
    }

    @Override
    public void validateForDelete(Integer id) {
        validateRelationship(id);
    }

    private void validateRelationship(Integer id) {
        List<Optional<RentalEntity>> rentals = rentalRepository.findByUserId(id);

        if (!rentals.isEmpty()) {
            throw new BusinessException("Não é possível deletar, pois, há pelo menos um aluguel associado a esse usuário.");
        }
    }
}
