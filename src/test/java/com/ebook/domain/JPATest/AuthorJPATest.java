package com.ebook.domain.JPATest;

import com.ebook.domain.Author;
import com.ebook.domain.Book;
import com.ebook.domain.Publisher;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorJPATest extends AbstractJPATest {

    private static final Logger logger = LoggerFactory.getLogger(AuthorJPATest.class);

    @Test
    public void createTest() {
        logger.info("Running createTest...");
        Author author = new Author("John Doe", "Fiction Writer", "American", LocalDate.of(1980, 5, 10));
        persistEntity(author);
        logger.info("Author successfully persisted in createTest.");
        Author readBackFromDatabaseForAssertion = findEntity(Author.class, author.getId());
        assertNotNull(readBackFromDatabaseForAssertion);
        assertEquals(author.getId(), readBackFromDatabaseForAssertion.getId());
        logger.info("createTest completed successfully.");
    }

    @Test
    public void readTest() {
        logger.info("Running readTest...");
        Author author = new Author("Jane Austen", "Classic Novelist", "British", LocalDate.of(1775, 12, 16));
        persistEntity(author);

        Author findAuthor = em.createQuery("SELECT a FROM Author a WHERE a.name = 'Jane Austen'", Author.class)
                .getSingleResult();

        assertNotNull(findAuthor);
        assertEquals("Jane Austen", findAuthor.getName());
        logger.info("readTest completed successfully.");
    }

    @Test
    public void updateTest() {
        logger.info("Running updateTest...");
        Author author = new Author("Mark Twain", "Humorist and Satirist", "American", LocalDate.of(1835, 11, 30));
        persistEntity(author);

        String updatedBio = "Famous author of 'The Adventures of Tom Sawyer'";
        tx.begin();
        author.setBio(updatedBio);
        tx.commit();

        logger.info("Author bio updated successfully in updateTest.");
        Author updatedAuthor = findEntity(Author.class, author.getId());
        assertEquals(updatedBio, updatedAuthor.getBio());
        logger.info("updateTest completed successfully.");
    }

    @Test
    public void deleteTest() {
        logger.info("Running deleteTest...");
        Author author = new Author("Emily Brontë", "Poet and novelist", "British", LocalDate.of(1818, 7, 30));
        persistEntity(author);

        Author findAuthor = findEntity(Author.class, author.getId());
        assertNotNull(findAuthor);

        removeEntity(findAuthor);
        Author deletedAuthor = findEntity(Author.class, author.getId());
        assertNull(deletedAuthor);
        logger.info("deleteTest completed successfully.");
    }

    @Test
    public void addAndRemoveBooksTest() {
        logger.info("Running addAndRemoveBooksTest...");
        Author author = new Author("J.K. Rowling", "Author of Harry Potter", "British", LocalDate.of(1965, 7, 31));
        Book book1 = new Book(1999,2,100,"english","JungleBook","harry","2000000000000");
        Book book2 = new Book(1990, 20, 200, "english", "HarryPotter", "jhon", "3000000000000");

        Publisher publisher1 = new Publisher("publisher","address","publisher@gmail.com","1234567890");
        persistEntity(publisher1);
        Publisher publisher2 = new Publisher("publisher2","address3","publisher3@gmail.com","12345678903");
        persistEntity(publisher2);

        book1.setPublisher(publisher1);
        book2.setPublisher(publisher2);

        persistEntity(book1);
        persistEntity(book2);
        persistEntity(author);

        // Add books
        tx.begin();
        author.addBook(book1);
        author.addBook(book2);
        tx.commit();

        Author updatedAuthor = findEntity(Author.class, author.getId());
        assertNotNull(updatedAuthor);
        List<Book> books = updatedAuthor.getBooks();
        assertEquals(2, books.size());

        // Remove a book
        tx.begin();
        author.removeBook(book1);
        tx.commit();

        updatedAuthor = findEntity(Author.class, author.getId());
        assertEquals(1, updatedAuthor.getBooks().size());
        logger.info("addAndRemoveBooksTest completed successfully.");
    }
}
