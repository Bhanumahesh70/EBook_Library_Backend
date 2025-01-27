package com.ebook.service;

import Repository.BookRepository;
import com.ebook.domain.Book;
import org.springframework.stereotype.Service;

@Service
public class BookService extends AbstractCRUDService<Book,Long>{

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        super(bookRepository);
        this.bookRepository=bookRepository;
    }

    public Book findByTitle(String title){
        return bookRepository.findByName(title);
    }

    @Override
    public void update(Long id, Book updatedBook) {
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
        book.setAuthors(updatedBook.getAuthors());  // Assuming authors are updated via the setter method
        book.setCategories(updatedBook.getCategories());  // Assuming categories are updated via the setter method
        book.setPublisher(updatedBook.getPublisher());  // Assuming publisher is updated via the setter method

        // Save the updated book to the repository
        bookRepository.save(book);
    }
}
