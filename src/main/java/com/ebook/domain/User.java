package com.ebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "phoneNumber")
        }
)
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


    @Column(name = "phoneNumber")
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
    @JsonManagedReference("user-borrowedBooks")
    List<BorrowedBook> borrowedBooks;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("user-reservations")
    List<Reservation> reservations;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("user-fines")
    List<Fine> fines;



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

    /**
     * Entity RelationShips methods
     */
    public void addFine(Fine fine){
        if(fine==null){
            return;
        }
        if(this.fines==null){
            this.fines = new ArrayList<Fine>();
        }
        if(!this.fines.contains(fine)){
            this.fines.add(fine);
            fine.setUser(this);
        }

    }
    public void removeFine(Fine fine) {
        if(fine==null || this.fines ==null){
            return;
        }
        if (this.fines.contains(fine)) {
            this.fines.remove(fine);
            fine.setUser(null);
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
            reservation.setUser(this);
        }
    }

    // Remove a reservation
    public void removeReservation(Reservation reservation) {
        if (reservation == null || this.reservations == null) {
            return;
        }
        if (this.reservations.contains(reservation)) {
            this.reservations.remove(reservation);
            reservation.setUser(null);
        }
    }

    // Add a borrowed book
    public void addBorrowedBook(BorrowedBook borrowedBook) {
        if (borrowedBook == null) {
            return;
        }
        if (this.borrowedBooks == null) {
            this.borrowedBooks = new ArrayList<>();
        }
        if (!this.borrowedBooks.contains(borrowedBook)) {
            this.borrowedBooks.add(borrowedBook);
            borrowedBook.setUser(this);
        }
    }

    // Remove a borrowed book
    public void removeBorrowedBook(BorrowedBook borrowedBook) {
        if (borrowedBook == null || this.borrowedBooks == null) {
            return;
        }
        if (this.borrowedBooks.contains(borrowedBook)) {
            this.borrowedBooks.remove(borrowedBook);
            borrowedBook.setUser(null);
        }
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
    public List<Fine> getFines() {
        return fines;
    }

    public void setFines(List<Fine> fines) {
        this.fines = fines;
    }
}
