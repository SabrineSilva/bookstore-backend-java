package bookstore.back.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="author", nullable = false)
    private String author;

    @Column(name="launchDate", nullable = false)
    private Integer launchDate;

    @Column(name="amount", nullable = false)
    private Integer totalQuantity;


    @Column(name="is_deleted", nullable = false)
    private boolean isDeleted;


    @Column(name="availableQuantity")
    private Integer availableQuantity;

    @Column(name="totalRented")
    private Integer totalRented;


    @OneToMany(mappedBy = "publisher")
    private List<BookEntity> books;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="publisher_id", nullable = false)
    private PublisherEntity publisher;


}
