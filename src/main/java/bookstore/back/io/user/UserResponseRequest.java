package bookstore.back.io.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseRequest {
    private Integer id;
    private String name;
    private String city;
    private String email;
    private String address;

}
