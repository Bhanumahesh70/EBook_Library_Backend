package com.ebook.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "BorrowedBook")
@NamedQuery(name="BorrowedBook.findAll",query="select b from BorrowedBook b")
public class BorrowedBook extends AbstractClass {

    @Column(name = "borrow_date", nullable = false)
    @NotNull(message = "Borrow date is mandatory")
    private LocalDateTime borrowDate;

    @Column(name = "return_date", nullable = false)
    @NotNull(message = "Return date is mandatory")
    @FutureOrPresent(message = "Return date must be today or in the future")
    private LocalDateTime expectedReturnDate;

    @Column(name = "returned_on")
    private LocalDateTime returnedOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @NotNull(message = "Borrow status is mandatory")
    private BorrowStatus status;

    @Column(name = "total_cost", nullable = false)
    @NotNull(message = "Cost is mandatory")
    private Double totalCost;
    /**
     * Entity RelationShips
     */
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @JsonBackReference("user-borrowedBooks")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bookId")
    @JsonBackReference("book-borrowedBooks")
    private Book book;

    @OneToOne
    @JoinColumn(name = "fineId", nullable = false)
    @JsonManagedReference("borrowedBook-fine")
    private Fine fine;

    @OneToOne
    private Reservation reservation;

    public BorrowedBook() {
    }

    public BorrowedBook(LocalDateTime borrowDate, LocalDateTime expectedReturnDate, LocalDateTime returnedOn, BorrowStatus status, Double totalCost) {
        this.borrowDate = borrowDate;
        this.expectedReturnDate = expectedReturnDate;
        this.returnedOn = returnedOn;
        this.status = status;
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "BorrowedBook{" +
                "borrowDate=" + borrowDate +
                ", returnDate=" + expectedReturnDate +
                ", returnedOn=" + returnedOn +
                ", status=" + status +
//                ", user=" + user +
//                ", book=" + book +
//                ", fine=" + fine +
                '}';
    }

    /**
     * Getters and Setters
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Fine getFine() {
        return fine;
    }

    public void setFine(Fine fine) {
        this.fine = fine;
    }

    public @NotNull(message = "Borrow date is mandatory") LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(@NotNull(message = "Borrow date is mandatory") LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    public @NotNull(message = "Return date is mandatory") @FutureOrPresent(message = "Return date must be today or in the future") LocalDateTime getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(@NotNull(message = "Return date is mandatory") @FutureOrPresent(message = "Return date must be today or in the future") LocalDateTime expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public LocalDateTime getReturnedOn() {
        return returnedOn;
    }

    public void setReturnedOn(LocalDateTime returnedOn) {
        this.returnedOn = returnedOn;
    }

    public BorrowStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowStatus status) {
        this.status = status;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public @NotNull(message = "Cost is mandatory") Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(@NotNull(message = "Cost is mandatory") Double totalCost) {
        this.totalCost = totalCost;
    }
}
