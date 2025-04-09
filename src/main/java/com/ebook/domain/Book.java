package com.ebook.domain;

import com.ebook.controller.AbstractController;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@Component
@Entity
@Table(name = "Book",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "title"),
                @UniqueConstraint(columnNames = "isbn")
        }
)
@NamedQuery(name="Book.findAll",query="select b from Book b")
public class Book extends AbstractClass{

    private static final Logger logger = LoggerFactory.getLogger(Book.class);
    @Column(name = "title", nullable = false)
    @NotBlank(message = "Title is mandatory")
    private String title;

//    @Column(name = "author", nullable = false)
//    @NotBlank(message = "Author is mandatory")
//    private String author;

    @Column(name = "isbn", nullable = false, unique = true)
    @NotBlank(message = "ISBN is mandatory")
    @Pattern(regexp = "\\d{13}", message = "ISBN must be a 13-digit number")
    private String isbn; // Unique

    @Column(name = "language", nullable = false)
    @NotBlank(message = "Language is mandatory")
    private String language;

    @Column(name = "total_copies", nullable = false)
    @Min(value = 1, message = "Total copies must be at least 1")
    private int totalCopies;

    @Column(name = "available_copies", nullable = false)
    @Min(value = 0, message = "Available copies cannot be negative")
    private int availableCopies;

    @Column(name = "publication_year", nullable = false)
    @Min(value = 1000, message = "Publication year must be a valid year")
    @Max(value = 2025, message = "Publication year must be a valid year")
    private int publicationYear;

    /**
     * Entity RelationShips
     */

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("book-borrowedBooks")
   private List<BorrowedBook> borrowedBooks;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("book-reservations")
    private List<Reservation> reservations;

    @ManyToMany(mappedBy = "books",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
   // @JsonManagedReference("book-categories")
    @JsonIgnore
    private List<Category> categories;

    @ManyToMany(mappedBy ="books", cascade = {CascadeType.ALL})
//    @JsonManagedReference("book-authors")
    @JsonIgnore
    private List<Author> authors;

    @ManyToOne
    @JsonBackReference("book-publisher")
    //@JsonIgnore
    @JoinColumn(name = "publsiherId")
    private Publisher publisher;





    public Book() {
    }

    public Book(int publicationYear, int availableCopies, int totalCopies, String language, String title, String isbn) {
        this.publicationYear = publicationYear;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;
        this.language = language;
        this.title = title;
//        this.author = author;
        this.isbn = isbn;
    }

    /**
     * Entity RelationShips methods
     */
    public void addAuthor(Author author2){
        logger.info("Inside Book.addAuthor()");
       //ensure bidirectional methods
        //so we can either use book.addAuthor() or author.book()
        if(author2==null){
            logger.info("author is null. Returning null");
            return;
        }
        if(this.authors==null){
            logger.info("Creating new author arrayList");
            this.authors = new ArrayList<Author>();
        }

        //check if contians author or not
        logger.info("Checking if the the author is present in list");
        if(!this.authors.contains(author2)){
            logger.info("No");
            logger.info("book.authors.add({})",author2.getId());
            this.authors.add(author2);
            logger.info("Now book.authors:{}",this.authors.stream().map(Author::getId).toList());
            logger.info("->>Go To Authors to add book");
            author2.addBook(this);
//            if (!author2.getBooks().contains(this)) {
//                logger.info("author doesnt contain this book. so adding author to book. author.addBook()");
//                author2.addBook(this); // Ensure bidirectional consistency
//            }
        }

    }

    public void removeAuthor(Author author2){
        logger.info("Inside Book.removeAuthor()");
        if(author2==null || this.authors==null){
            logger.info("author is null. Returning null");
            return;
        }
        if(this.authors.contains(author2)){
            logger.info("Removing author {}",author2);
            this.authors.remove(author2);
            logger.info("After removal. Book.authors: {}",this.authors.toString());
            author2.removeBook(this);
        }
        logger.info("This book doesntContain the author:{}",author2);
    }

    public void addCategory(Category category){

        //ensure bidirectional methods
        if(category==null){
            return;
        }
        if(this.categories==null){
            this.categories = new ArrayList<>();
        }

        //check if contians category or not
        if(!this.categories.contains(category)){
            this.categories.add(category);
            category.addBook(this);
        }

    }

    public void removeCategory(Category category){
        if(category==null || this.categories==null){
            return;
        }
        if(this.categories.contains(category)){
            this.categories.remove(category);
            category.removeBook(this);
        }
    }

    // Add a reservation
    public void addReservation(Reservation reservation) {
        if (reservation == null) {
            return;
        }
        if (this.reservations == null) {
            this.reservations = new ArrayList<>();
        }
        if (!this.reservations.contains(reservation)) {
            this.reservations.add(reservation);
            reservation.setBook(this);
        }
    }

    // Remove a reservation
    public void removeReservation(Reservation reservation) {
        if (reservation == null || this.reservations == null) {
            return;
        }
        if (this.reservations.contains(reservation)) {
            this.reservations.remove(reservation);
            reservation.setBook(null);
        }
    }

    // Add a borrowed book
    public void addBorrowedBook(BorrowedBook borrowedBook) {
        if (borrowedBook == null) {
            return;
        }
        if (this.borrowedBooks == null) {
            this.borrowedBooks = new ArrayList<>();
            this.setBorrowedBooks(borrowedBooks);
        }
        if (!this.borrowedBooks.contains(borrowedBook)) {
            this.borrowedBooks.add(borrowedBook);
            borrowedBook.setBook(this);
        }
    }

    // Remove a borrowed book
    public void removeBorrowedBook(BorrowedBook borrowedBook) {
        if (borrowedBook == null || this.borrowedBooks == null) {
            return;
        }
        if (this.borrowedBooks.contains(borrowedBook)) {
            this.borrowedBooks.remove(borrowedBook);
            borrowedBook.setBook(null);
        }
    }
    @Override
    public String toString() {
        return "Book{" +
                "ID='" + id + '\'' +
                "titles='" + title + '\'' +
//
                ", isbn='" + isbn + '\'' +
                ", language='" + language + '\'' +
                ", totalCopies=" + totalCopies +
                ", availableCopies=" + availableCopies +
                ", publicationYear=" + publicationYear +
//                ", borrowedBooks=" + borrowedBooks +
//                ", reservations=" + reservations.toString() +
//                ", categories=" + categories.toString() +
//                ", authors=" + authors.toString() +
//                ", publisher=" + publisher.toString() +
                '}';
    }

    /**
     * Getters and Setters
     * @return
     */
    public List<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BorrowedBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        if(this.categories==categories){
            return;
        }
        if(this.categories!=null){
            for(Category category: this.categories){
                category.removeBook(this);
            }
        }
        if(categories!=null){
            for(Category category:categories){
                category.addBook(this);
            }
        }
        this.categories = categories;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        logger.info("Inside book.setAuthors()");
        if(this.authors==authors){
            logger.info("Already authors exits");
            return;
        }
        if(this.authors!=null){
            logger.info("New Author List. Removing Old author list");
            for(Author author: this.authors){
                author.removeBook(this);
            }
        }
        if(authors!=null){
            logger.info("Adding this book to each individual author for new List");
            for(Author author:authors){
                author.addBook(this);
            }
        }
        this.authors = authors;
        logger.info("Authors is set:",this.authors.stream().map(Author::getId).toList().toString());
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        if(this.publisher==null || this.publisher!=publisher){
            this.publisher = publisher;
            this.publisher.addBook(this);
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
}
