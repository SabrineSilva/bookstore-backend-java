package bookstore.back.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users_tb")
public class UserEntity extends BaseEntity {

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="city", nullable = false)
    private String city;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="address", nullable = false)
    private String address;

    @Column(name="numberOfRentals")
    private Integer numberOfRentals;

    @Column(name="is_deleted", nullable = false)
    private boolean isDeleted;
}
