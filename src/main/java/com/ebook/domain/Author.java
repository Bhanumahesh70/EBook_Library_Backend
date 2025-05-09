package com.ebook.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Author",
        uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
@NamedQuery(name="Author.findAll",query="select a from Author a")
@NamedQuery(name = "Author.findByName", query ="select a from Author a where a.name=: aname")
public class Author extends AbstractClass{

    private static final Logger logger = LoggerFactory.getLogger(Author.class);
    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Column(name = "bio", length = 1000)
    @Size(max = 1000, message = "Bio must not exceed 1000 characters")
    private String bio;

    @Column(name = "nationality", length = 50)
    @Size(max = 50, message = "Nationality must not exceed 50 characters")
    private String nationality;

    @Column(name = "birth_date")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @Column(name = "cover_image_path")
    private String coverImagePath;

    /**
     * Entity RelationShips
     */
   @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="Authors_Book",
            joinColumns =@JoinColumn(name="AuthorId"),
            inverseJoinColumns = @JoinColumn(name = "BookId")
    )
  // @JsonBackReference("book-authors")
   @JsonIgnore
    private List<Book> books;

    public Author() {
    }

    public Author(String name, String bio, String nationality, LocalDate birthDate) {

        this.name = name;
        this.bio = bio;
        this.nationality = nationality;
        this.birthDate = birthDate;
    }

    /**
     * Entity RelationShips methods
     */
    public void addBook(Book book){
        logger.info("Inside Author.addBook():");
        if(book==null){
            logger.info("Book is null so returning");
            return;
        }
        if(this.books==null){
            logger.info("creating new Book List");
            this.books = new ArrayList<Book>();
        }

        logger.info("Checking if book {} is present in list",book.getId());
        logger.info("Book:{}",book);
        if(!this.books.contains(book)){
            logger.info("No");
            logger.info("Author.books.add({})",book.getId());
            this.books.add(book);
            logger.info("now author.books:{}",this.books.stream().map(Book::getId).toList());
            book.addAuthor(this);
//            if (!book.getAuthors().contains(this)) {
//                logger.info("Book doesnt contain this author. book.addAuthor() ");
//                book.addAuthor(this); // Ensure bidirectional consistency
//            }
        }
        logger.info("Books Present in the Author.getBook():{}",this.getBooks().stream().map(Book::getId).toList());
        logger.info("Books: {}",this.getBooks());

    }
    public void removeBook(Book book) {
        if(book==null || this.books ==null){
            return;
        }
        if (this.books.contains(book)) {
            this.books.remove(book);
            book.removeAuthor(this);
        }
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", nationality='" + nationality + '\'' +
                ", birthDate=" + birthDate +
                ", books=" + books +
                '}';
    }

    /**
     * Getters and Setters
     * @return
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        logger.info("author.setBooks()");
        if(this.books == books){
            logger.info("Books list are already existing. So returning");
            return;
        }
        if(this.books!=null){
            logger.info("A new book List. Removing old books from list");
            for(Book book: this.books){
                book.removeAuthor(this);
            }
        }
        if(books!=null){
            logger.info("adding this author to new books list");
            for(Book book:books){
                book.addAuthor(this);
            }
        }
        this.books = books;
        logger.info("set Method-Author.Books()",this.books.stream().map(Book::getId).toList().toString());
    }
    public void setCoverImagePath(String coverImagePath){
        this.coverImagePath = coverImagePath;
    }
    public String getCoverImagePath(){
        return this.coverImagePath = coverImagePath;
    }
}
