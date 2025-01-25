package com.ebook.domain.JPATest;

import com.ebook.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;


public class BorrowedBookJPATest extends AbstractJPATest {

    private User user;
    private Publisher publisher;
    private Book book;
    private Fine fine;

    private static final Logger logger = LoggerFactory.getLogger(BorrowedBookJPATest.class);


    @BeforeEach
    public void initialSetUp(){
        user = new User("Jane Doe 2", "jane.doe2@email.com", "password456", "987654321", "456 Oak St", UserRole.LIBRARIAN);
        persistEntity(user);

        publisher = new Publisher("publisher","address","publisher@gmail.com","1234567890");
        persistEntity(publisher);

        book = new Book(1999,2,100,"english","JungleBook","harry","2000000000000");
        book.setPublisher(publisher);

        fine = new Fine(100,FinePaidStatus.PAID,LocalDateTime.now());
        fine.setUser(user);
        persistEntity(fine);

    }
    @Test
    public void createTest(){
        BorrowedBook borrowedBook = new BorrowedBook(LocalDateTime.now(),LocalDateTime.now().plusDays(7),null, BorrowStatus.BORROWED);
        borrowedBook.setUser(user);
        borrowedBook.setBook(book);
        borrowedBook.setFine(fine);
        persistEntity(borrowedBook);
        BorrowedBook findBorrowedBook = findEntity(BorrowedBook.class,borrowedBook.getId());
        Assertions.assertNotNull(findBorrowedBook);
        Assertions.assertEquals(borrowedBook.getId(),findBorrowedBook.getId());
    }

}
