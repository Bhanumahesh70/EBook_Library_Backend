package com.ebook.service;

import com.ebook.Repository.BookRepository;
import com.ebook.Repository.BorrowedBookRepository;
import com.ebook.domain.Book;
import com.ebook.dto.BookDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService extends AbstractCRUDService<Book,BookDTO,Long>{

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;
    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    @Autowired
    public BookService(BookRepository bookRepository){
        super(bookRepository);
        this.bookRepository=bookRepository;
    }

    public Book findByTitle(String title){
        return bookRepository.findByName(title);
    }

    @Override
    public Book patchUpdate(Long id, BookDTO bookDTO){
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Cannot find Book with id:" + id));
        if (bookDTO.getTitle() != null) book.setTitle(bookDTO.getTitle());
        if (bookDTO.getAuthor() != null) book.setAuthor(bookDTO.getAuthor());
        if(bookDTO.getIsbn()!=null) book.setIsbn(bookDTO.getIsbn());
        if(bookDTO.getLanguage()!=null) book.setLanguage(bookDTO.getLanguage());
        if (bookDTO.getTotalCopies() > 0) book.setTotalCopies(bookDTO.getTotalCopies());
        if(bookDTO.getPublicationYear()>0) book.setPublicationYear(bookDTO.getPublicationYear());
        return bookRepository.save(book);
    }

    @Override
    public BookDTO convertToDTO(Book book){
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setLanguage(book.getLanguage());
        dto.setTotalCopies(book.getTotalCopies());
        dto.setAvailableCopies(book.getAvailableCopies());
        dto.setPublicationYear(book.getPublicationYear());
        return dto;
    }

    @Override
    public Book convertToEntity(BookDTO bookDTO){
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setLanguage(bookDTO.getLanguage());
        book.setTotalCopies(bookDTO.getTotalCopies());
        book.setAvailableCopies(bookDTO.getAvailableCopies());
        book.setPublicationYear(bookDTO.getPublicationYear());
        return book;
    }

    @Override
    public Book update(Long id, Book updatedBook) {
        // Retrieve the existing book from the repository
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Cannot find Book with id:" + id));

        // Update the fields of the existing book
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setIsbn(updatedBook.getIsbn());
        book.setLanguage(updatedBook.getLanguage());
        book.setTotalCopies(updatedBook.getTotalCopies());
        book.setAvailableCopies(updatedBook.getAvailableCopies());
        book.setPublicationYear(updatedBook.getPublicationYear());
//        book.setAuthors(updatedBook.getAuthors());  // Assuming authors are updated via the setter method
//        book.setCategories(updatedBook.getCategories());  // Assuming categories are updated via the setter method
//        book.setPublisher(updatedBook.getPublisher());  // Assuming publisher is updated via the setter method

        // Save the updated book to the repository
        return bookRepository.save(book);
    }

//    @Override
//    public void delete(Long id){
//        Book book = bookRepository.findById(id).orElseThrow(() ->
//                new RuntimeException("Cannot find Book with id:" + id));
////        List<BorrowedBook> borrowedBookList = book.getBorrowedBooks();
////        for(BorrowedBook b: borrowedBookList){
////            //book.removeBorrowedBook(b);
////            b.setBook(null);
////            logger.info("BorroweBook: "+b);
////            logger.info("saved above borrowedBook");
////            borrowedBookRepository.delete(b);
////        }
//        logger.info("setting  book.setBorrowedBooks(null) ");
//        book.setBorrowedBooks(null);
//        bookRepository.deleteById(id);
//    }
}
