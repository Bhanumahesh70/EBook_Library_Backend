package com.ebook.domain.JPATest;

import com.ebook.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationJPATest extends AbstractJPATest {

    private static final Logger logger = LoggerFactory.getLogger(ReservationJPATest.class);

    private User user;
    private Publisher publisher;
    private Book book;

    @BeforeEach
    public void initialSetUp() {
        logger.info("Setting up test data...");

        SecureRandom secureRandom = new SecureRandom();

        // Generate unique values for user, publisher, and book fields
        String uniqueUserEmail = Integer.toString(secureRandom.nextInt()) + "@gmail.com";
        String uniqueUserPhone = "9785" + Integer.toString(100000 + secureRandom.nextInt(999999));
        user = new User("John Doe", uniqueUserEmail, "password123", uniqueUserPhone, "123 Maple St", UserRole.USER);
        persistEntity(user);

        String uniquePublisherName = "Publisher" + Integer.toString(secureRandom.nextInt());
        String uniquePublisherEmail = Integer.toString(secureRandom.nextInt()) + "@gmail.com";
        String uniquePublisherPhone = "9785" + Integer.toString(100000 + secureRandom.nextInt(999999));
        publisher = new Publisher(uniquePublisherName, "123 Main St", uniquePublisherEmail, uniquePublisherPhone);
        persistEntity(publisher);

        String uniqueBookTitle = "Book" + Integer.toString(secureRandom.nextInt());
        String uniqueBookIsbn = "1000000" + Integer.toString(100000 + secureRandom.nextInt(99999));
        book = new Book(2023, 1, 300, "English", uniqueBookTitle, "Author Name", uniqueBookIsbn);
        book.setPublisher(publisher);
        persistEntity(book);

        logger.info("Test data setup completed.");
    }

    @Test
    public void createTest() {
        logger.info("Running createTest...");

        Reservation reservation = new Reservation(LocalDateTime.now(), ReservationStatus.ACTIVE);
        reservation.setUser(user);
        reservation.setBook(book);
        persistEntity(reservation);

        Reservation readFromDb = findEntity(Reservation.class, reservation.getId());
        assertNotNull(readFromDb);
        assertEquals(reservation.getId(), readFromDb.getId());
        logger.info("createTest completed successfully.");
    }

    @Test
    public void readTest() {
        logger.info("Running readTest...");

        Reservation reservation = new Reservation(LocalDateTime.now(), ReservationStatus.CANCELLED);
        reservation.setUser(user);
        reservation.setBook(book);
        persistEntity(reservation);

        Reservation findReservation = em.createQuery("SELECT r FROM Reservation r WHERE r.book.isbn = :isbn", Reservation.class)
                .setParameter("isbn", book.getIsbn())
                .getSingleResult();

        assertNotNull(findReservation);
        assertEquals(book.getIsbn(), findReservation.getBook().getIsbn());
        logger.info("readTest completed successfully.");
    }

    @Test
    public void updateTest() {
        logger.info("Running updateTest...");

        Reservation reservation = new Reservation(LocalDateTime.now(), ReservationStatus.ACTIVE);
        reservation.setUser(user);
        reservation.setBook(book);
        persistEntity(reservation);

        Reservation findReservation = findEntity(Reservation.class, reservation.getId());

        tx.begin();
        ReservationStatus updatedStatus = ReservationStatus.CANCELLED;
        findReservation.setStatus(updatedStatus);
        em.merge(findReservation);
        tx.commit();

        Reservation updatedReservation = findEntity(Reservation.class, reservation.getId());
        assertEquals(updatedStatus, updatedReservation.getStatus());
        logger.info("updateTest completed successfully.");
    }

    @Test
    public void deleteTest() {
        logger.info("Running deleteTest...");

        Reservation reservation = new Reservation(LocalDateTime.now(), ReservationStatus.ACTIVE);
        reservation.setUser(user);
        reservation.setBook(book);
        persistEntity(reservation);

        Reservation findReservation = findEntity(Reservation.class, reservation.getId());
        assertNotNull(findReservation);

        removeEntity(findReservation);

        Reservation deletedReservation = findEntity(Reservation.class, reservation.getId());
        assertNull(deletedReservation);
        logger.info("deleteTest completed successfully.");
    }
}
