package com.ebook.domain.JPATest;

import com.ebook.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class BorrowedBookJPATest extends AbstractJPATest {

    private static final Logger logger = LoggerFactory.getLogger(BorrowedBookJPATest.class);

    private User user;
    private Publisher publisher;
    private Book book;
    private Fine fine;

    @BeforeEach
    public void initialSetUp() {
        logger.info("Setting up test data...");

        SecureRandom secureRandom = new SecureRandom();

        // Generate unique values for user, publisher, and book fields
        String uniqueUserEmail = Integer.toString(secureRandom.nextInt()) + "@gmail.com";
        String uniqueUserPhone = "9785" + Integer.toString(100000 + secureRandom.nextInt(999999));
        user = new User("Jane Doe", uniqueUserEmail, "password456", uniqueUserPhone, "456 Oak St", UserRole.ROLE_LIBRARIAN);
        persistEntity(user);

        String uniquePublisherName = "publisher"+Integer.toString(secureRandom.nextInt());
        String uniquePublisherEmail = Integer.toString(secureRandom.nextInt()) + "@gmail.com";
        String uniquePublisherPhone = "9785" + Integer.toString(100000 + secureRandom.nextInt(999999));
        publisher = new Publisher(uniquePublisherName, "address", uniquePublisherEmail, uniquePublisherPhone);
        persistEntity(publisher);

        String uniqueBookTitle = "Book"+Integer.toString(secureRandom.nextInt());
        String uniqueBookIsbn = "1000000" + Integer.toString(100000 + secureRandom.nextInt(99999));
        book = new Book(1999, 2, 100, "English", uniqueBookTitle, uniqueBookIsbn);
        book.setPublisher(publisher);
        persistEntity(book);

        fine = new Fine(100, FinePaidStatus.PAID, LocalDateTime.now());
        fine.setUser(user);
        persistEntity(fine);

        logger.info("Test data setup completed.");
    }

    @Test
    public void createTest() {
        logger.info("Running createTest...");
        BorrowedBook borrowedBook = new BorrowedBook(LocalDateTime.now(), LocalDateTime.now().plusDays(7), null, BorrowStatus.BORROWED, 0.0);
        borrowedBook.setUser(user);
        borrowedBook.setBook(book);
        borrowedBook.setFine(fine);

        persistEntity(borrowedBook);

        BorrowedBook readFromDb = findEntity(BorrowedBook.class, borrowedBook.getId());
        assertNotNull(readFromDb);
        assertEquals(borrowedBook.getId(), readFromDb.getId());
        logger.info("createTest completed successfully.");
    }

    @Test
    public void readTest() {
        logger.info("Running readTest...");
        BorrowedBook borrowedBook = new BorrowedBook(LocalDateTime.now(), LocalDateTime.now().plusDays(7), null, BorrowStatus.BORROWED, 0.0);
        borrowedBook.setUser(user);
        borrowedBook.setBook(book);
        borrowedBook.setFine(fine);

        persistEntity(borrowedBook);

        BorrowedBook findBorrowedBook = em.createQuery("SELECT bb FROM BorrowedBook bb WHERE bb.book.isbn = :isbn", BorrowedBook.class)
                .setParameter("isbn", book.getIsbn())
                .getSingleResult();

        assertNotNull(findBorrowedBook);
        assertEquals(book.getIsbn(), findBorrowedBook.getBook().getIsbn());
        logger.info("readTest completed successfully.");
    }

    @Test
    public void updateTest() {
        logger.info("Running updateTest...");
        BorrowedBook borrowedBook = new BorrowedBook(LocalDateTime.now(), LocalDateTime.now().plusDays(7), null, BorrowStatus.BORROWED, 0.0);
        borrowedBook.setUser(user);
        borrowedBook.setBook(book);
        borrowedBook.setFine(fine);

        persistEntity(borrowedBook);

        BorrowedBook findBorrowedBook = findEntity(BorrowedBook.class, borrowedBook.getId());

        tx.begin();
        LocalDateTime returnDate = LocalDateTime.now().plusDays(5);
        findBorrowedBook.setExpectedReturnDate(returnDate);
        em.merge(findBorrowedBook);
        tx.commit();

        BorrowedBook updatedBorrowedBook = findEntity(BorrowedBook.class, borrowedBook.getId());
        assertNotNull(updatedBorrowedBook.getExpectedReturnDate());
        assertEquals(returnDate,updatedBorrowedBook.getExpectedReturnDate());
        logger.info("updateTest completed successfully.");
    }

    @Test
    public void deleteTest() {
        logger.info("Running deleteTest...");
        BorrowedBook borrowedBook = new BorrowedBook(LocalDateTime.now(), LocalDateTime.now().plusDays(7), null, BorrowStatus.BORROWED, 0.0);
        borrowedBook.setUser(user);
        borrowedBook.setBook(book);
        borrowedBook.setFine(fine);

        persistEntity(borrowedBook);

        BorrowedBook findBorrowedBook = findEntity(BorrowedBook.class, borrowedBook.getId());
        assertNotNull(findBorrowedBook);

        removeEntity(findBorrowedBook);

        BorrowedBook deletedBorrowedBook = findEntity(BorrowedBook.class, borrowedBook.getId());
        assertNull(deletedBorrowedBook);
        logger.info("deleteTest completed successfully.");
    }
}
