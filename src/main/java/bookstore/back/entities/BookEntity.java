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
@Table(name = "books_tb")
public class BookEntity extends BaseEntity {

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="author", nullable = false)
    private String author;

    @Column(name="launchDate", nullable = false)
    private Integer launchDate;

    @Column(name="totalQuantity", nullable = false)
    private Integer totalQuantity;

    @Column(name="availableQuantity")
    private Integer availableQuantity;

    @Column(name="totalTimesRented")
    private Integer totalTimesRented;

    @OneToMany(mappedBy = "publisher")
    private List<BookEntity> books;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="publisher_id", nullable = false)
    private PublisherEntity publisher;

    @Column(name="is_deleted", nullable = false)
    private boolean isDeleted;


}
