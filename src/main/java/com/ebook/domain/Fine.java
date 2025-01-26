package com.ebook.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@Entity
@Table(name = "Fine")
@NamedQuery(name="Fine.findAll",query="select f from Fine f")
public class Fine extends AbstractClass{

    @Column(name = "amount", nullable = false)
    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount must be positive")
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @NotNull(message = "Fine status is mandatory")
    private FinePaidStatus status;

    @Column(name = "paid_date")
    @PastOrPresent(message = "Paid date must be in the past or present")
    private LocalDateTime paidDate;

    @OneToOne(mappedBy ="fine" )
    private BorrowedBook borrowedBook;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public Fine() {
    }

    public Fine(double amount, FinePaidStatus status, LocalDateTime paidDate) {
        this.amount = amount;
        this.status = status;
        this.paidDate = paidDate;
    }



    @Override
    public String toString() {
        return "Fine{" +
                "amount=" + amount +
                ", status=" + status +
                ", paidDate=" + paidDate +
                ", borrowedBook=" + borrowedBook +
                ", user=" + user +
                '}';
    }

    /**
     * Getters and Setters
     * @return
     */

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public FinePaidStatus getStatus() {
        return status;
    }

    public void setStatus(FinePaidStatus status) {
        this.status = status;
    }

    public LocalDateTime getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDateTime paidDate) {
        this.paidDate = paidDate;
    }

    public BorrowedBook getBorrowedBook() {
        return borrowedBook;
    }

    public void setBorrowedBook(BorrowedBook borrowedBook) {
        this.borrowedBook = borrowedBook;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
