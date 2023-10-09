package bookstore.back.io.user;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class UserDetailRequest extends UserResponseRequest {

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
