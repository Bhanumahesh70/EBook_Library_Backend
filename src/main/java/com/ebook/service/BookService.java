package com.ebook.service;

import com.ebook.Repository.BookRepository;
import com.ebook.Repository.BorrowedBookRepository;
import com.ebook.domain.Author;
import com.ebook.domain.Book;
import com.ebook.domain.Category;
import com.ebook.domain.Publisher;
import com.ebook.dto.AuthorDTO;
import com.ebook.dto.BookDTO;
import com.ebook.dto.CategoryDTO;
import com.ebook.dto.PublisherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class BookService extends AbstractCRUDService<Book,BookDTO,Long>{


    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;
   // private final BorrowedBookRepository borrowedBookRepository;
    private  final CategoryService categoryService;
    private  final AuthorService authorService;
    private final PublisherService publisherService;

    @Value("${server.url:http://localhost:8080}")
    private String serverUrl;

    @Autowired
    public BookService(BookRepository bookRepository, CategoryService categoryService, AuthorService authorService, PublisherService publisherService){
        super(bookRepository);
        this.bookRepository=bookRepository;
        this.categoryService=categoryService;
        this.publisherService = publisherService;
        this.authorService = authorService;
    }

    public Book findByTitle(String title){
        return bookRepository.findByName(title);
    }

    protected <T, D> void syncAssociations(
            List<T> currentList,
            List<D> updatedDetails,
            java.util.function.Function<D, T> findByIdFn,
            java.util.function.Consumer<T> addFn,
            java.util.function.Consumer<T> removeFn
    ) {
        List<T> updatedEntities = updatedDetails.stream().map(findByIdFn).toList();
        List<T> toRemove = new ArrayList<>();
        for (T current : currentList) {
            if (!updatedEntities.contains(current)) {
                toRemove.add(current);
            }
        }
        toRemove.forEach(removeFn);

        for (T updated : updatedEntities) {
            if (!currentList.contains(updated)) {
                addFn.accept(updated);
            }
        }
    }
    public Book addBookImage(Long id, MultipartFile file) {
        try {
            Book book = this.findById(id);

            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path filePath = Paths.get("uploads", filename);

            // Ensure the directory exists
            Files.createDirectories(filePath.getParent());

            // Copy file to the target location
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Set the path and save the book
            book.setCoverImagePath(filename);
            logger.info("Book Image Path: {}", book.getCoverImagePath());

            return bookRepository.save(book);

        } catch (IOException e) {
            logger.error("Failed to store file for book ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to upload cover image for book ID " + id, e);
        } catch (Exception e) {
            logger.error("Unexpected error while uploading book image for ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Unexpected error during book image upload", e);
        }
    }
    @Override
    public Book patchUpdate(Long id, BookDTO bookDTO){
        logger.info("BookDTO: {}",bookDTO.toString());
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Cannot find Book with id:" + id));
        if (bookDTO.getTitle() != null) book.setTitle(bookDTO.getTitle());
      //  if (bookDTO.getAuthor() != null) book.setAuthor(bookDTO.getAuthor());
        if(bookDTO.getIsbn()!=null) book.setIsbn(bookDTO.getIsbn());
        if(bookDTO.getLanguage()!=null) book.setLanguage(bookDTO.getLanguage());
        if (bookDTO.getTotalCopies() > 0) book.setTotalCopies(bookDTO.getTotalCopies());
        if(bookDTO.getAvailableCopies()>0) book.setAvailableCopies(bookDTO.getAvailableCopies());
        if(bookDTO.getPublicationYear()>0) book.setPublicationYear(bookDTO.getPublicationYear());
        if(bookDTO.getPublisherDetails()!=null){
            Publisher publisher = publisherService.findById(bookDTO.getPublisherDetails().getId());
            book.setPublisher(publisher);
        }
        if (bookDTO.getAuthorsDetails() != null) {
            syncAssociations(
                    book.getAuthors(),
                    bookDTO.getAuthorsDetails(),
                    authorDTO -> authorService.findById(authorDTO.getId()),
                    book::addAuthor,
                    book::removeAuthor
            );
        }

        if (bookDTO.getCategoriesDetails() != null) {
            syncAssociations(
                    book.getCategories(),
                    bookDTO.getCategoriesDetails(),
                    categoryDTO -> categoryService.findById(categoryDTO.getId()),
                    book::addCategory,
                    book::removeCategory
            );
        }
        return bookRepository.save(book);
    }

    @Override
    public BookDTO convertToDTO(Book book){
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setLanguage(book.getLanguage());
        dto.setTotalCopies(book.getTotalCopies());
        dto.setAvailableCopies(book.getAvailableCopies());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setAuthorsDetails(book.getAuthors().stream().map(author -> new AuthorDTO(author.getId(), author.getName())).toList());
        dto.setCategoriesDetails(book.getCategories().stream().map(category -> new CategoryDTO(category.getId(),category.getCategoryName())).toList());
        dto.setPublisherDetails(new PublisherDTO(book.getPublisher().getId(),book.getPublisher().getName()));
       // dto.setPublisherId(book.getPublisher().getId());
       //dto.setCategoryIds(book.getCategories().stream().map(Category::getId).toList());
       //dto.setCategoriesDTO(book.getCategories().stream().map(category -> categoryService.convertToDTO(category)).toList());


        dto.setCoverImageUrl(serverUrl + "/ebook/books/cover/" + book.getCoverImagePath());


        return dto;
    }

    @Override
    public Book convertToEntity(BookDTO bookDTO){
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setLanguage(bookDTO.getLanguage());
        book.setTotalCopies(bookDTO.getTotalCopies());
        book.setAvailableCopies(bookDTO.getAvailableCopies());
        book.setPublicationYear(bookDTO.getPublicationYear());
        Publisher publisher = publisherService.findById(bookDTO.getPublisherDetails().getId());
        List<Author> authors = bookDTO.getAuthorsDetails().stream().map(authorDetail-> authorService.findById(authorDetail.getId())).toList();
        List<Category> categories = bookDTO.getCategoriesDetails().stream().map(categoryDetail->categoryService.findById(categoryDetail.getId())).toList();
        book.setPublisher(publisher);
        book.setAuthors(authors);
        book.setCategories(categories);
        return book;
    }

    @Override
    public Book update(Long id, Book updatedBook) {
        // Retrieve the existing book from the repository
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Cannot find Book with id:" + id));
        book.setTitle(updatedBook.getTitle());
       // book.setAuthor(updatedBook.getAuthor());
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
