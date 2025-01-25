package com.ebook.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Publisher",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name"),
                @UniqueConstraint(columnNames = "email")
        }
)
@NamedQuery(name="Publisher.findAll",query="select p from Publisher p")
public class Publisher extends AbstractClass {

    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Column(name = "address", length = 255)
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @Column(name = "email", nullable = false, length = 100)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Please provide a valid email address")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @Column(name = "phone_number", length = 15)
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be between 10 and 15 digits and can optionally start with a '+'")
    private String phoneNumber;

    @OneToMany(mappedBy = "publisher",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;

    public Publisher() {
    }

    public Publisher(String name, String address, String email, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void addBook(Book book){
        if(this.books==null){
            this.books = new ArrayList<Book>();
        }
        this.books.add(book);
        book.setPublisher(this);
    }

    public void removeBook(Book book){
        if(this.books != null){
            this.books.remove(book);
            book.setPublisher(null);
        }
    }
    @Override
    public String toString() {
        return "Publisher{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        //remove existing books relationship to the publisher
        if(this.books!=null){
            for(Book book : this.books){
                book.setPublisher(null);
            }
        }

        // Add new book relationships to the publisher
        if(books!=null)
        {
            for(Book book: books){
                book.setPublisher(this);
            }
        }

        this.books = books;
    }
}
