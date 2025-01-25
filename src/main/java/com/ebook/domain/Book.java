package com.ebook.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
@Table(name = "Book",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "title"),
                @UniqueConstraint(columnNames = "isbn")
        }
)
@NamedQuery(name="Book.findAll",query="select b from Book b")
public class Book extends AbstractClass{

    @Column(name = "title", nullable = false)
    @NotBlank(message = "Title is mandatory")
    private String title;

    @Column(name = "author", nullable = false)
    @NotBlank(message = "Author is mandatory")
    private String author;

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
   private List<BorrowedBook> borrowedBooks;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

    @ManyToMany(mappedBy = "books")
    private List<Category> categories;

    @ManyToMany(mappedBy ="books" )
    private List<Author> authors;

    @ManyToOne
    @JoinColumn(name = "publsiherId",nullable = false)
    private Publisher publisher;





    public Book() {
    }

    public Book(int publicationYear, int availableCopies, int totalCopies, String language, String title, String author, String isbn) {
        this.publicationYear = publicationYear;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;
        this.language = language;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", language='" + language + '\'' +
                ", totalCopies=" + totalCopies +
                ", availableCopies=" + availableCopies +
                ", publicationYear=" + publicationYear +
                ", borrowedBooks=" + borrowedBooks +
                ", reservations=" + reservations +
                ", categories=" + categories +
                ", authors=" + authors +
                ", publisher=" + publisher +
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
        this.categories = categories;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

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
