package com.ebook.domain.JPATest;

import com.ebook.domain.Publisher;
import com.ebook.domain.Book;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class PublisherJPATest extends AbstractJPATest{

    private static final Logger logger = LoggerFactory.getLogger(PublisherJPATest.class);

    @Test
    public void createTest() {
        logger.info("Running createTest...");

        // Create a publisher
        Publisher publisher = new Publisher("O'Reilly Media", "1005 Gravenstein Highway", "contact@oreilly.com", "+14155552671");

        // Create a book and associate it with the publisher
        Book book = new Book(1999,2,100,"english","JungleBook","harry","2000000000000");
        publisher.addBook(book); // Associate book with publisher

        // Persist publisher (and book will be persisted due to cascade)
        persistEntity(publisher);
        logger.info("Publisher successfully persisted in createTest.");

        Publisher readBackFromDatabaseForAssertion = findEntity(Publisher.class, publisher.getId());
        assertNotNull(readBackFromDatabaseForAssertion);
        assertEquals(publisher.getId(), readBackFromDatabaseForAssertion.getId());
        assertEquals(publisher.getName(), readBackFromDatabaseForAssertion.getName());
        assertEquals(publisher.getBooks().size(), 1); // Ensure the book is also persisted
        logger.info("createTest completed successfully.");
    }

    @Test
    public void readTest() {
        logger.info("Running readTest...");

        // Create and persist a publisher
        Publisher publisher = new Publisher("Packt Publishing", "2000 Town Center, Suite 300", "support@packtpub.com", "+18005553333");
        Book book = new Book(1999,2,100,"english","HarryPotter","JK","3000000000000");
        publisher.addBook(book);
        persistEntity(publisher);
        logger.info("Publisher successfully persisted in ReadTest.");
        // Retrieve the publisher by email
        Publisher foundPublisher = em.createQuery("SELECT p FROM Publisher p WHERE p.email = 'support@packtpub.com'", Publisher.class)
                .getSingleResult();
        logger.info("Publisher retrived.");
        assertNotNull(foundPublisher);
        assertEquals("Packt Publishing", foundPublisher.getName());
        logger.info("readTest completed successfully.");
    }

    @Test
    public void updateTest() {
        logger.info("Running updateTest...");

        // Create and persist a publisher
        Publisher publisher = new Publisher("Addison-Wesley", "75 Arlington Street", "info@aw.com", "+14155553333");
        persistEntity(publisher);

        // Update publisher's address
        String newAddress = "1500 Broadway Street";
        tx.begin();
        publisher.setAddress(newAddress);
        tx.commit();

        Publisher updatedPublisher = findEntity(Publisher.class, publisher.getId());
        assertEquals(newAddress, updatedPublisher.getAddress());
        logger.info("updateTest completed successfully.");
    }

    @Test
    public void deleteTest() {
        logger.info("Running deleteTest...");

        // Create and persist a publisher
        Publisher publisher = new Publisher("Pearson", "5000 Square Tower", "contact@pearson.com", "+14155554444");
        persistEntity(publisher);

        // Ensure the publisher was persisted
        Publisher findPublisher = findEntity(Publisher.class, publisher.getId());
        assertNotNull(findPublisher);

        // Remove the publisher and ensure it's deleted
        removeEntity(findPublisher);
        Publisher deletedPublisher = findEntity(Publisher.class, publisher.getId());
        assertNull(deletedPublisher);

        logger.info("deleteTest completed successfully.");
    }
}
