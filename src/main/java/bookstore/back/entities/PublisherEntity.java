package bookstore.back.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.awt.print.Book;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="publishers")
public class PublisherEntity extends BaseEntity  {


    @Column(name="name", unique = true, nullable = false)
    private String name;

    @Column(name="city", nullable = false)
    private String city;

    @Column(name="is_deleted", nullable = false)
    private boolean isDeleted;

}

