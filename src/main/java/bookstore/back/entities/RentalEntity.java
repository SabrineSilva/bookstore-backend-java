package bookstore.back.entities;

import bookstore.back.io.rental.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="rentals_tb")
public class RentalEntity extends BaseEntity {

    @Column(name = "rentalDate")
    private LocalDate rentalDate;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @Column(name = "returnDate")
    private LocalDate returnDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "bookId", nullable = false)
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;
}
