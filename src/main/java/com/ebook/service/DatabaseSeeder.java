package com.ebook.service;

import com.ebook.Repository.*;
import com.ebook.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);
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

    private final JdbcTemplate jdbcTemplate;

    public DatabaseSeeder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        System.out.println("Database Seeder in run");
/**

        // Create Publisher
        Publisher publisher = new Publisher("O'Reilly Media", "1005 Gravenstein Highway", "contact@oreilly.com", "+14155552671");
        System.out.println("creted publisher");
        publisherRepository.save(publisher);
        System.out.println("saved publisher");
        // Create Author
        Author author = new Author("Jane Austen1", "Classic Novelist", "British", LocalDate.of(1775, 12, 16));
        authorRepository.save(author);
        logger.info("Database Seeder->Author is saved with id: {}",author.getId());

        // Create Category
        Category category = new Category("Fiction1", "A category for fictional books");
        categoryRepository.save(category);
        // Create Book
        Book book = new Book(1999, 2, 100, "English", "Jungle Book", "Harry", "2000000000000");
        logger.info("Database Seeder->newbook created.");
        book.addAuthor(author);
        book.addCategory(category);
        //book.setPublisher has bidirectional method to add a book to publisher
        book.setPublisher(publisher);
        //publisher.addBook(book);

        // persisting book will also persist author, category as cascadeType.persist and merge were enabled
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
**/
        //Inserting another sample of data
        // Create Publisher
        Publisher publisher1 = new Publisher("J.B. Lippincott & Co.", "Philadelphia, PA", "contact@lippincott.com", "+12125551234");
        Publisher publisher2 = new Publisher("Secker & Warburg", "London, UK", "contact@seckerwarburg.com", "+441632960961");
        Publisher publisher3 = new Publisher("T. Egerton, Whitehall", "London, UK", "contact@egerton.com", "+441632960962");
        Publisher publisher4 = new Publisher("Charles Scribner's Sons", "New York, NY", "contact@scribners.com", "+12125551235");
        Publisher publisher5 = new Publisher("Harper & Brothers", "New York, NY", "contact@harperbrothers.com", "+12125551236");
        Publisher publisher6 = new Publisher("Little, Brown and Company", "Boston, MA", "contact@littlebrown.com", "+12125551237");
        Publisher publisher7 = new Publisher("George Allen & Unwin", "London, UK", "contact@georgeallen.com", "+441632960963");
        Publisher publisher8 = new Publisher("HarperCollins", "New York, NY", "contact@harpercollins.com", "+12125551238");
        Publisher publisher9 = new Publisher("Chatto & Windus", "London, UK", "contact@chatto.com", "+441632960964");

        publisherRepository.save(publisher1);
        publisherRepository.save(publisher2);
        publisherRepository.save(publisher3);
        publisherRepository.save(publisher4);
        publisherRepository.save(publisher5);
        publisherRepository.save(publisher6);
        publisherRepository.save(publisher7);
        publisherRepository.save(publisher8);
        publisherRepository.save(publisher9);

        // Create Authors
        Author author1 = new Author("Harper Lee", "American Novelist", "American", LocalDate.of(1926, 4, 28));
        Author author2 = new Author("George Orwell", "English Novelist", "British", LocalDate.of(1903, 6, 25));
        Author author3 = new Author("Jane Austen", "Classic Novelist", "British", LocalDate.of(1775, 12, 16));
        Author author4 = new Author("F. Scott Fitzgerald", "American Novelist", "American", LocalDate.of(1896, 9, 24));
        Author author5 = new Author("Herman Melville", "American Novelist", "American", LocalDate.of(1819, 8, 1));
        Author author6 = new Author("J.D. Salinger", "American Novelist", "American", LocalDate.of(1919, 1, 1));
        Author author7 = new Author("J.R.R. Tolkien", "English Novelist", "British", LocalDate.of(1892, 1, 3));
        Author author8 = new Author("Paulo Coelho", "Brazilian Novelist", "Brazilian", LocalDate.of(1947, 8, 24));
        Author author9 = new Author("Aldous Huxley", "English Novelist", "British", LocalDate.of(1894, 7, 26));

        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);
        authorRepository.save(author4);
        authorRepository.save(author5);
        authorRepository.save(author6);
        authorRepository.save(author7);
        authorRepository.save(author8);
        authorRepository.save(author9);

        // Create Categories
        Category fiction = new Category("Fiction", "A category for fictional books");
        Category dystopian = new Category("Dystopian", "A category for dystopian books");
        Category romance = new Category("Romance", "A category for romantic novels");
        Category fantasy = new Category("Fantasy", "A category for fantasy books");
        Category classic = new Category("Classic", "Classic literature");
        categoryRepository.save(fiction);
        categoryRepository.save(dystopian);
        categoryRepository.save(romance);
        categoryRepository.save(fantasy);
        categoryRepository.save(classic);

        // Create Books
        logger.info("creating Book1");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        Book book1 = new Book(1960, 1, 281, "English", "To Kill a Mockingbird", "9780061120084");
        Author authorb11 = authorRepository.findById(Long.valueOf(1)).orElseThrow(() -> new RuntimeException("Entity not found with id: 2"));
        Author authorb12 = authorRepository.findById(Long.valueOf(2)).orElseThrow(() -> new RuntimeException("Entity not found with id: 3"));
        Author authorb13 = authorRepository.findById(Long.valueOf(3)).orElseThrow(() -> new RuntimeException("Entity not found with id: 4"));

        book1.addAuthor(authorb11);
        book1.addAuthor(authorb12);
        book1.addAuthor(authorb13);
        book1.addCategory(fiction);
        book1.addCategory(dystopian);
        book1.addCategory(romance);
        book1.setPublisher(publisher1);
        bookRepository.save(book1);
        logger.info("Book1 is created");
        logger.info("Book1:{}",book1);
        logger.info("book1.getAuthors()= {}", book1.getAuthors().stream().map(Author::getId).toList());

        logger.info("creating Book2");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        Book book2 = new Book(1949, 1, 328, "English", "1984", "9780451524935");
        Author authorb21 = authorRepository.findById(Long.valueOf(1)).orElseThrow(() -> new RuntimeException("Entity not found with id: 2"));
        Author authorb22 = authorRepository.findById(Long.valueOf(2)).orElseThrow(() -> new RuntimeException("Entity not found with id: 3"));
        Author authorb23 = authorRepository.findById(Long.valueOf(3)).orElseThrow(() -> new RuntimeException("Entity not found with id: 4"));
        book2.addAuthor(authorb22);
        book2.addCategory(dystopian);
        book2.setPublisher(publisher2);
        bookRepository.save(book2);
        logger.info("Book2 is created");
        logger.info("Book2:{}",book2);
        logger.info("Book1:{}",book1);
        logger.info("book2.getAuthors()={} ", book2.getAuthors().stream().map(Author::getId).toList());

        logger.info("creating Book3");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        Book book3 = new Book(1813, 1, 279, "English", "Pride and Prejudice", "9781503290563");
        Author authorb33 = authorRepository.findById(Long.valueOf(3)).orElseThrow(() -> new RuntimeException("Entity not found with id: 4"));
        book3.addAuthor(authorb33);
        book3.addCategory(romance);
        book3.setPublisher(publisher3);
        bookRepository.save(book3);
        logger.info("Book3 is created");
        logger.info("Book3:{}",book3);
        logger.info("book3.getAuthors()= {}", book3.getAuthors().stream().map(Author::getId).toList());

        logger.info("creating Book4");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        Book book4 = new Book(1925, 1, 180, "English", "The Great Gatsby",  "9780743273565");
        book4.addAuthor(author4);
        book4.addCategory(classic);
        book4.setPublisher(publisher4);
        bookRepository.save(book4);
        logger.info("Book4 is created");
        logger.info("Book4:{}",book4);
        logger.info("book4.getAuthors()= {}", book4.getAuthors().stream().map(Author::getId).toList());

        logger.info("creating Book5");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        Book book5 = new Book(1851, 1, 635, "English", "Moby Dick","9781503280786");
        book5.addAuthor(author5);
        book5.addCategory(fiction);
        book5.setPublisher(publisher5);
        bookRepository.save(book5);
        logger.info("Book5 is created");
        logger.info("book5.getAuthors()= {}", book5.getAuthors().stream().map(Author::getId).toList());

        logger.info("creating Book6");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        Book book6 = new Book(1951, 1, 277, "English", "The Catcher in the Rye",  "9780316769488");
        book6.addAuthor(author6);
        book6.addCategory(classic);
        book6.setPublisher(publisher6);
        bookRepository.save(book6);
        logger.info("Book6 is created");
        logger.info("book6.getAuthors()= {}", book6.getAuthors().stream().map(Author::getId).toList());

        logger.info("creating Book7");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        Book book7 = new Book(1937, 1, 310, "English", "The Hobbit",  "9780618968633");
        book7.addAuthor(author7);
        book7.addCategory(fantasy);
        book7.setPublisher(publisher7);
        bookRepository.save(book7);
        logger.info("Book1 is created");
        logger.info("book1.getAuthors()= {}", book1.getAuthors().stream().map(Author::getId));

        logger.info("creating Book8");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        Book book8 = new Book(1954, 1, 423, "English", "The Fellowship of the Ring",  "9780618574940");
        book8.addAuthor(author7);
        book8.addCategory(fantasy);
        book8.setPublisher(publisher7);
        bookRepository.save(book8);
        logger.info("Book1 is created");
        logger.info("book1.getAuthors()= {}", book1.getAuthors().stream().map(Author::getId));

        logger.info("creating Book9");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        Book book9 = new Book(1988, 1, 208, "English", "The Alchemist",  "9780061122415");
        book9.addAuthor(author8);
        book9.addCategory(fiction);
        book9.setPublisher(publisher8);
        bookRepository.save(book9);
        logger.info("Book1 is created");
        logger.info("book1.getAuthors()= {}", book1.getAuthors().stream().map(Author::getId));

        logger.info("creating Book10");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        logger.info("/////////////////////////////////////////////////////////////////////////////////////");
        Book book10 = new Book(1932, 1, 268, "English", "Brave New World", "9780060850524");
        book10.addAuthor(author9);
        book10.addCategory(dystopian);
        book10.setPublisher(publisher9);
        bookRepository.save(book10);
        logger.info("Book1 is created");
        logger.info("book1.getAuthors()= {}", book1.getAuthors().stream().map(Author::getId));

        // Creating users
        User user1 = new User("Jane Doe", "jane.doe@email.com", "password456", "987654320", "456 Oak St", UserRole.ROLE_USER);
        userRepository.save(user1);
        User user2 = new User("John Smith", "john.smith@email.com", "password123", "987654321", "123 Maple St", UserRole.ROLE_LIBRARIAN);
        userRepository.save(user2);
        User user3 = new User("Alice Johnson", "alice.johnson@email.com", "alicePass", "987654322", "789 Pine St", UserRole.ROLE_USER);
        userRepository.save(user3);
        User user4 = new User("Bob Brown", "bob.brown@email.com", "bobPasswd", "987654323", "567 Elm St", UserRole.ROLE_LIBRARIAN);
        userRepository.save(user4);
        User user5 = new User("Charlie White", "charlie.white@email.com", "charliePass", "987654324", "321 Birch St", UserRole.ROLE_USER);
        userRepository.save(user5);
        User user6 = new User("Emily Davis", "emily.davis@email.com", "emilyPass", "987654325", "654 Cedar St", UserRole.ROLE_USER);
        userRepository.save(user6);
        User user7 = new User("admin", "admin@email.com", "adminadmin", "934654325", "654 Cedar St", UserRole.ROLE_ADMIN);
        userRepository.save(user7);
        User user8 = new User("user", "user@email.com", "user", "987123325", "354 Cedar St", UserRole.ROLE_USER);
        userRepository.save(user8);
        User user9 = new User("librarian", "librarian@email.com", "librarian", "677123325", "4564 Cedar St", UserRole.ROLE_LIBRARIAN);
        userRepository.save(user9);



        // Creating fines
        Fine fine1 = new Fine(20.0, FinePaidStatus.UNPAID, null);
        fine1.setUser(user1);
        fineRepository.save(fine1);

        Fine fine2 = new Fine(15.0, FinePaidStatus.PAID, LocalDateTime.now());
        fine2.setUser(user2);
        fineRepository.save(fine2);
        Fine fine3 = new Fine(30.0, FinePaidStatus.UNPAID, null);
        fine3.setUser(user3);
        fineRepository.save(fine3);

        // Creating borrowed books
        BorrowedBook borrowedBook1 = new BorrowedBook(LocalDateTime.now(), LocalDateTime.now().plusDays(7), null, BorrowStatus.BORROWED);
        borrowedBook1.setUser(user1);
        borrowedBook1.setBook(book1); // book1
        borrowedBook1.setFine(fine1);
        borrowedBookRepository.save(borrowedBook1);

        BorrowedBook borrowedBook2 = new BorrowedBook(LocalDateTime.now(), LocalDateTime.now().plusDays(7), null, BorrowStatus.BORROWED);
        borrowedBook2.setUser(user2);
        borrowedBook2.setBook(book2); // book2
        borrowedBook2.setFine(fine2);
        borrowedBookRepository.save(borrowedBook2);

        BorrowedBook borrowedBook3 = new BorrowedBook(LocalDateTime.now(), LocalDateTime.now().plusDays(10), null, BorrowStatus.BORROWED);
        borrowedBook3.setUser(user3);
        borrowedBook3.setBook(book3); // book3
        borrowedBook3.setFine(fine3);
        borrowedBookRepository.save(borrowedBook3);



        // Creating reservations
        Reservation reservation1 = new Reservation(LocalDateTime.now(), ReservationStatus.ACTIVE);
        reservation1.setUser(user1);
        reservation1.setBook(book4); // book4
        reservationRepository.save(reservation1);

        Reservation reservation2 = new Reservation(LocalDateTime.now(), ReservationStatus.ACTIVE);
        reservation2.setUser(user1);
        reservation2.setBook(book5); // book5
        reservationRepository.save(reservation2);

        Reservation reservation3 = new Reservation(LocalDateTime.now(), ReservationStatus.ACTIVE);
        reservation3.setUser(user2);
        reservation3.setBook(book6); // book6
        reservationRepository.save(reservation3);

        Reservation reservation4 = new Reservation(LocalDateTime.now(), ReservationStatus.ACTIVE);
        reservation4.setUser(user3);
        reservation4.setBook(book7); // book7
        reservationRepository.save(reservation4);

        Reservation reservation5 = new Reservation(LocalDateTime.now(), ReservationStatus.ACTIVE);
        reservation5.setUser(user3);
        reservation5.setBook(book8); // book8
        reservationRepository.save(reservation5);

        Reservation reservation6 = new Reservation(LocalDateTime.now(), ReservationStatus.ACTIVE);
        reservation6.setUser(user4);
        reservation6.setBook(book9); // book9
        reservationRepository.save(reservation6);

        Reservation reservation7 = new Reservation(LocalDateTime.now(), ReservationStatus.ACTIVE);
        reservation7.setUser(user5);
        reservation7.setBook(book10); // book10
        reservationRepository.save(reservation7);

        System.out.println("Sample second data for books and other data inserted into the database.");


    }
}
