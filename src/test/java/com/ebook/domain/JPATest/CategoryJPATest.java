package com.ebook.domain.JPATest;

import com.ebook.domain.Book;
import com.ebook.domain.Category;
import com.ebook.domain.Publisher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryJPATest extends AbstractJPATest {

    @Test
    public void createTest() {
        logger.info("Running createTest...");
        Category category = new Category("Fiction", "A category for fictional books");
        persistEntity(category);
        logger.info("Category successfully persisted in createTest.");

        Category retrievedCategory = findEntity(Category.class, category.getId());
        assertNotNull(retrievedCategory);
        assertEquals(category.getCategoryName(), retrievedCategory.getCategoryName());
        logger.info("createTest completed successfully.");
    }

    @Test
    public void readTest() {
        logger.info("Running readTest...");
        Category category = new Category("Science", "Books related to science topics");
        persistEntity(category);

        Category retrievedCategory = em.createQuery("SELECT c FROM Category c WHERE c.categoryName = 'Science'", Category.class)
                .getSingleResult();
        assertNotNull(retrievedCategory);
        assertEquals("Science", retrievedCategory.getCategoryName());
        logger.info("readTest completed successfully.");
    }

    @Test
    public void updateTest() {
        logger.info("Running updateTest...");
        Category category = new Category("History", "Books about historical events");
        persistEntity(category);

        String newDescription = "Updated description for historical books";
        tx.begin();
        category.setDescription(newDescription);
        tx.commit();
        logger.info("Category description updated successfully in updateTest.");

        Category updatedCategory = findEntity(Category.class, category.getId());
        assertEquals(newDescription, updatedCategory.getDescription());
        logger.info("updateTest completed successfully.");
    }

    @Test
    public void deleteTest() {
        logger.info("Running deleteTest...");
        Category category = new Category("Adventure", "Books about adventures");
        persistEntity(category);

        Category retrievedCategory = findEntity(Category.class, category.getId());
        assertNotNull(retrievedCategory);

        removeEntity(retrievedCategory);
        Category deletedCategory = findEntity(Category.class, category.getId());
        assertNull(deletedCategory);
        logger.info("deleteTest completed successfully.");
    }

    @Test
    public void addBookTest() {
        logger.info("Running addBookTest...");
        Category category = new Category("Mystery", "Books about mysteries");
        Book book = new Book(2020, 5, 5, "English", "The Mystery Book", "1234567890123");
        Publisher publisher1 = new Publisher("publisher","address","publisher@gmail.com","1234567890");

        persistEntity(publisher1);
        persistEntity(category);
        book.setPublisher(publisher1);
        persistEntity(book);

        tx.begin();
        category.addBook(book);
        tx.commit();
        logger.info("Book successfully added to category in addBookTest.");

        Category updatedCategory = findEntity(Category.class, category.getId());
        assertTrue(updatedCategory.getBooks().contains(book));
        logger.info("addBookTest completed successfully.");
    }

    @Test
    public void removeBookTest() {
        logger.info("Running removeBookTest...");
        Category category = new Category("Thriller", "Books full of thrill and suspense");
        Book book = new Book(2019, 10, 10, "English", "The Thriller Book",  "9876543210987");
        Publisher publisher2 = new Publisher("publisher2","address3","publisher3@gmail.com","12345678903");

        persistEntity(publisher2);
        persistEntity(category);
        book.setPublisher(publisher2);
        persistEntity(book);

        tx.begin();
        category.addBook(book);
        tx.commit();

        tx.begin();
        category.removeBook(book);
        tx.commit();
        logger.info("Book successfully removed from category in removeBookTest.");

        Category updatedCategory = findEntity(Category.class, category.getId());
        assertFalse(updatedCategory.getBooks().contains(book));
        logger.info("removeBookTest completed successfully.");
    }
}
