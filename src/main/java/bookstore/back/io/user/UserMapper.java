package bookstore.back.io.user;

import bookstore.back.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseRequest toUserResponseRequest(UserEntity user){
        UserResponseRequest response = new UserResponseRequest();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setCity(user.getCity());
        response.setEmail(user.getEmail());
        response.setAddress(user.getAddress());
        return response;
    }

    public UserDetailRequest toUserDetailRequest(UserEntity user){
        UserDetailRequest response = new UserDetailRequest();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setCity(user.getCity());
        response.setEmail(user.getEmail());
        response.setAddress(user.getAddress());
        response.setNumberOfRentals(user.getNumberOfRentals());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }

}
