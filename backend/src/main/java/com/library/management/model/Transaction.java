package com.library.management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnoreProperties({"transactions"})
    private Student student;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIgnoreProperties({"transactions"})
    private Book book;
    
    @Column(nullable = false)
    private LocalDate borrowDate;
    
    @Column
    private LocalDate returnDate;
    
    @Column(nullable = false)
    private boolean returned = false;
    
    @Column
    private Double fine = 0.0;
    
    public Transaction(Student student, Book book, LocalDate borrowDate) {
        this.student = student;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returned = false;
        this.fine = 0.0;
    }
    
    public void recordBorrow() {
        this.borrowDate = LocalDate.now();
        this.returned = false;
        System.out.println("Borrow recorded for book: " + book.getTitle());
    }
    
    public void recordReturn() {
        this.returnDate = LocalDate.now();
        this.returned = true;
        this.fine = calculateFine();
        System.out.println("Return recorded for book: " + book.getTitle() + " with fine: KSh " + this.fine);
    }
    
    public double calculateFine() {
        if (returned && returnDate != null) {
            long daysBorrowed = ChronoUnit.DAYS.between(borrowDate, returnDate);
            if (daysBorrowed > 30) {
                long overdueDays = daysBorrowed - 30;
                double calculatedFine = overdueDays * 50.0;
                this.fine = calculatedFine;
                return calculatedFine;
            }
        } else if (!returned) {
            long daysBorrowed = ChronoUnit.DAYS.between(borrowDate, LocalDate.now());
            if (daysBorrowed > 30) {
                long overdueDays = daysBorrowed - 30;
                return overdueDays * 50.0;
            }
        }
        return 0.0;
    }
    
    public LocalDate getDueDate() {
        return borrowDate.plusDays(30);
    }
}
