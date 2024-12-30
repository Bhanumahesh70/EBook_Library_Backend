package com.ebook.domain;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BorrowedBook {

    private LocalDate borrowDate;
    private LocalDate returnDate;
    private LocalDate returnedOn;
    private BorrowStatus status;
    private BigDecimal fine;

    /**
     * Entity RelationShips
     */
   @ManyToOne
   @JoinColumn(name = "userId",nullable = false)
   private User user;

    @ManyToOne
    @JoinColumn(name = "bookId",nullable = false)
    private Book book;

    public BorrowedBook(LocalDate borrowDate, LocalDate returnDate, LocalDate returnedOn, BorrowStatus status, BigDecimal fine) {
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.returnedOn = returnedOn;
        this.status = status;
        this.fine = fine;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getReturnedOn() {
        return returnedOn;
    }

    public void setReturnedOn(LocalDate returnedOn) {
        this.returnedOn = returnedOn;
    }

    public BorrowStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowStatus status) {
        this.status = status;
    }

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }
}
