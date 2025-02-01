package com.ebook.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
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

    /**
     * Entity RelationShips
     */
   @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="Authors_Book",
            joinColumns =@JoinColumn(name="AuthorId"),
            inverseJoinColumns = @JoinColumn(name = "BookId")
    )
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
        if(book==null){
            return;
        }
        if(this.books==null){
            this.books = new ArrayList<Book>();
        }
        if(!this.books.contains(book)){
            this.books.add(book);
            book.addAuthor(this);
        }

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
        if(this.books == books){
            return;
        }
        if(this.books!=null){
            for(Book book: this.books){
                book.removeAuthor(this);
            }
        }
        if(books!=null){
            for(Book book:books){
                book.addAuthor(this);
            }
        }
        this.books = books;
    }

}
