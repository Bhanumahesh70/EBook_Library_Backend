package com.ebook.service;

import com.ebook.Repository.*;
import com.ebook.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private FineRepository fineRepository;
    @Autowired
    private BorrowedBookRepository borrowedBookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create Author
        Author author = new Author("Jane Austen", "Classic Novelist", "British", LocalDate.of(1775, 12, 16));
        authorRepository.save(author);

        // Create Category
        Category category = new Category("Fiction", "A category for fictional books");
        categoryRepository.save(category);

        // Create Publisher
        Publisher publisher = new Publisher("O'Reilly Media", "1005 Gravenstein Highway", "contact@oreilly.com", "+14155552671");
        publisherRepository.save(publisher);

        // Create Book
        Book book = new Book(1999, 2, 100, "English", "Jungle Book", "Harry", "2000000000000");
        book.addAuthor(author);
        book.addCategory(category);
        book.setPublisher(publisher);
        bookRepository.save(book);

        // Create User
        User user = new User("Jane Doe", "jane.doe@email.com", "password456", "987654320", "456 Oak St", UserRole.LIBRARIAN);
        userRepository.save(user);

        // Create Fine
        Fine fine = new Fine(20.0, FinePaidStatus.UNPAID, null);
        fine.setUser(user);
        fineRepository.save(fine);

        // Create Borrowed Book
        BorrowedBook borrowedBook = new BorrowedBook(LocalDateTime.now(), LocalDateTime.now().plusDays(7), null, BorrowStatus.BORROWED);
        borrowedBook.setUser(user);
        borrowedBook.setBook(book);
        borrowedBook.setFine(fine);
        borrowedBookRepository.save(borrowedBook);

        // Create Reservation
        Reservation reservation = new Reservation(LocalDateTime.now(), ReservationStatus.ACTIVE);
        reservation.setUser(user);
        reservation.setBook(book);
        reservationRepository.save(reservation);

        System.out.println("Sample data inserted into the database.");
    }
}
