package com.ebook.domain.JPATest;

import com.ebook.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FineJPATest extends AbstractJPATest {

    private static final Logger logger = LoggerFactory.getLogger(FineJPATest.class);
    private User user;
    private Publisher publisher;
    private Book book;
    private Fine fine;
    private BorrowedBook borrowedBook;
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

        borrowedBook = new BorrowedBook(LocalDateTime.now(), LocalDateTime.now().plusDays(7), null, BorrowStatus.BORROWED,0.0);
        borrowedBook.setUser(user);
        borrowedBook.setBook(book);
        borrowedBook.setFine(fine);
        persistEntity(borrowedBook);

        logger.info("Test data setup completed.");
    }

    @Test
    public void createTest() {
        logger.info("Running createTest...");

        Fine fine = new Fine(20.0, FinePaidStatus.UNPAID, null);
        fine.setUser(user);
        fine.setBorrowedBook(borrowedBook);

        persistEntity(fine);
        logger.info("Fine successfully persisted in createTest.");

        Fine readBackFromDatabaseForAssertion = findEntity(Fine.class, fine.getId());
        assertNotNull(readBackFromDatabaseForAssertion);
        assertEquals(fine.getId(), readBackFromDatabaseForAssertion.getId());
        assertEquals(fine.getAmount(), readBackFromDatabaseForAssertion.getAmount());
        assertEquals(fine.getStatus(), readBackFromDatabaseForAssertion.getStatus());
        logger.info("createTest completed successfully.");
    }

    @Test
    public void readTest() {
        logger.info("Running readTest...");

        Fine fine = new Fine(10.0, FinePaidStatus.PAID, LocalDateTime.now());
        fine.setUser(user);
        fine.setBorrowedBook(borrowedBook);

        persistEntity(fine);

        // Retrieve the fine created
        Fine foundFine = em.createQuery("SELECT f FROM Fine f WHERE f.amount = 10.0", Fine.class)
                .getSingleResult();

        assertNotNull(foundFine);
        assertEquals(10.0, foundFine.getAmount());
        assertEquals(FinePaidStatus.PAID, foundFine.getStatus());
        logger.info("readTest completed successfully.");
    }

    @Test
    public void updateTest() {
        logger.info("Running updateTest...");

        Fine fine = new Fine(15.0, FinePaidStatus.UNPAID, null);
        fine.setUser(user);
        fine.setBorrowedBook(borrowedBook);

        persistEntity(fine);

        // Update the fine status to PAID
        Fine updatedFine = findEntity(Fine.class, fine.getId());
        tx.begin();
        updatedFine.setStatus(FinePaidStatus.PAID);
        updatedFine.setPaidDate(LocalDateTime.now());
        tx.commit();

        Fine updatedFineFromDb = findEntity(Fine.class, fine.getId());
        assertEquals(FinePaidStatus.PAID, updatedFineFromDb.getStatus());
        assertNotNull(updatedFineFromDb.getPaidDate());
        logger.info("updateTest completed successfully.");
    }

    @Test
    public void deleteTest() {
        logger.info("Running deleteTest...");

        Fine fine = new Fine(5.0, FinePaidStatus.UNPAID, null);
        fine.setUser(user);
        fine.setBorrowedBook(borrowedBook);

        persistEntity(fine);

        Fine findFine = findEntity(Fine.class, fine.getId());
        assertNotNull(findFine);

        removeEntity(findFine);
        Fine deletedFine = findEntity(Fine.class, fine.getId());
        assertNull(deletedFine);
        logger.info("deleteTest completed successfully.");
    }
}
