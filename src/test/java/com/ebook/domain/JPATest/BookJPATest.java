package com.ebook.domain.JPATest;

import com.ebook.domain.Book;
import com.ebook.domain.Publisher;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class BookJPATest extends AbstractJPATest{

    @Test
    public void createTest(){

        Book book = new Book(1999,2,100,"english","JungleBook","harry","2000000000000");
        Publisher publisher = new Publisher("publisher","address","publisher@gmail.com","1234567890");
        book.setPublisher(publisher);
        persistEntity(publisher);
        persistEntity(book);
        Book readFromDb = findEntity(Book.class, book.getId());
        assertNotNull(readFromDb);
        assertEquals(book.getId(),readFromDb.getId());

    }

    @Test
    public void readTest(){
        Book book = new Book(1990,20,200,"english","HarryPotter","jhon","3000000000000");
        Publisher publisher = new Publisher("publisher","address","publisher@gmail.com","1234567890");
        book.setPublisher(publisher);
        publisher.setBooks(book);
        persistEntity(publisher);
        persistEntity(book);
        Book findBook = em.createQuery("SELECT b FROM b WHERE b.title = 'HarryPotter'", Book.class).getSingleResult();
        assertNotNull(findBook);
        assertEquals("HarryPotter",findBook.getTitle());
    }

    @Test
    public void updateTest(){
        Book book = new Book(1970,300,1200,"english","LordOfTheRing","raju","5000000000000");
        Publisher publisher = new Publisher("publisher","address","publisher@gmail.com","1234567890");
        book.setPublisher(publisher);
        persistEntity(book);
        Book findBook = em.createQuery("SELECT b FROM b WHERE b.title = 'HarryPotter'", Book.class).getSingleResult();
        tx.begin();
        findBook.setTitle("Lord of The Rings ");
        em.merge(findBook);
        tx.commit();
        Book updatedBook = findEntity(Book.class, findBook.getId());
        assertEquals("Lord of The Rings",updatedBook.getTitle() );
    }

    @Test
    public void deleteTest(){
        Book book = new Book(2004,50,5000,"english","PeterEngland","peter","7000000000000");
        Publisher publisher = new Publisher("publisher","address","publisher@gmail.com","1234567890");
        book.setPublisher(publisher);
        persistEntity(book);
        Book findBook = findEntity(Book.class, book.getId());
        assertNotNull(findBook);
        removeEntity(book);
        Book deletedBook = findEntity(Book.class,book.getId());
        assertNull(deletedBook);

    }
}
