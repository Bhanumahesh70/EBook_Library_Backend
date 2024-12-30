package com.ebook.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.lang.NonNull;

import java.util.List;

@Entity
@Table(name = "User")
@NamedQuery(name="User.findAll",query="select u from User u")
public class User extends AbstractClass  {


    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;


    @Email(message = "Invalid Email address")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Size(min= 8, message = "Password Should be minimum 8 characters")
    @Column(name = "password", nullable = false)
    private String password;


    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    /**
     * Entity RelationShips
     */
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    List<BorrowedBook> borrowedBooks;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    List<Reservation> reservations;

    public User() {
    }

    public User(String name, String email, String password, String phoneNumber, String address, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", role=" + role +
                ", borrowedBooks=" + borrowedBooks +
                ", reservations=" + reservations +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
