package com.ebook.domain.JPATest;

import com.ebook.domain.Book;
import com.ebook.domain.Publisher;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class BookJPATest extends AbstractJPATest{

    private Publisher publisher;


    @BeforeEach
    public void publisherSetUp(){
        publisher = new Publisher("publisher","address","publisher@gmail.com","1234567890");
        persistEntity(publisher);
    }


    @AfterEach
    public void publisherCleanUp(){
        /**
         *
         *   first remove books as we cannot remove publisher as it has a foreign key constraint with book
         */

        List<Book> books = em.createQuery("SELECT b FROM Book b WHERE  b.publisher.id = :publisherId",Book.class)
                .setParameter("publisherId",publisher.getId())
                .getResultList();

        for(Book book:books){
            removeEntity(book);
        }
        Publisher p = findEntity(Publisher.class,publisher.getId());
        if(p!=null){
            removeEntity(p);
        }
    }


    @Test
    public void createTest(){

        Book book = new Book(1999,2,100,"english","JungleBook","harry","2000000000000");
        book.setPublisher(publisher);
        persistEntity(book);
        Book readFromDb = findEntity(Book.class, book.getId());
        assertNotNull(readFromDb);
        assertEquals(book.getId(),readFromDb.getId());
//        removeEntity(book);


    }


    @Test
    public void readTest() {
        Book book = new Book(1990, 20, 200, "english", "HarryPotter", "jhon", "3000000000000");
        book.setPublisher(publisher);
        // publisher.addBook(book);
        persistEntity(book);
        Book findBook = em.createQuery("SELECT b FROM Book b WHERE b.title = 'HarryPotter'", Book.class).getSingleResult();
        assertNotNull(findBook);
        assertEquals("HarryPotter", findBook.getTitle());


    }


    @Test
    public void updateTest(){
        Book book = new Book(1970,300,1200,"english","LordOfTheRing","raju","5000000000000");
        book.setPublisher(publisher);
        persistEntity(book);
        Book findBook = em.createQuery("SELECT b FROM Book b WHERE b.title = 'LordOfTheRing'", Book.class).getSingleResult();
        tx.begin();
        findBook.setTitle("Lord of The Rings");
        em.merge(findBook);
        tx.commit();
        Book updatedBook = findEntity(Book.class, book.getId());
        assertEquals("Lord of The Rings",updatedBook.getTitle() );


    }


    @Test
    public void deleteTest(){
        Book book = new Book(2004,50,5000,"english","PeterEngland","peter","7000000000000");
        book.setPublisher(publisher);
        persistEntity(book);
        Book findBook = findEntity(Book.class, book.getId());
        assertNotNull(findBook);
        removeEntity(book);
        Book deletedBook = findEntity(Book.class,book.getId());
        assertNull(deletedBook);

    }
}
