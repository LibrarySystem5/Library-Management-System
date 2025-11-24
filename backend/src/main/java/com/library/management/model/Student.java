package com.library.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {
    
    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();
    
    public Student(String name, String email, String password) {
        super(null, name, email, password, "STUDENT");
    }
    
    @Override
    public void register() {
        System.out.println("Student registered: " + getName());
    }
    
    @Override
    public boolean login(String email, String password) {
        return this.getEmail().equals(email) && this.getPassword().equals(password);
    }
    
    @Override
    public void viewBooks(List<Book> books) {
        System.out.println("Student viewing " + books.size() + " books");
    }
    
    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            book.setAvailable(false);
            System.out.println("Book borrowed: " + book.getTitle());
        } else {
            System.out.println("Book not available: " + book.getTitle());
        }
    }
    
    public void returnBook(Book book, Transaction transaction) {
        book.setAvailable(true);
        transaction.setReturned(true);
        System.out.println("Book returned: " + book.getTitle());
    }
    
    public List<Book> searchBooks(String keyword, List<Book> allBooks) {
        List<Book> results = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }
    
    public double viewFines() {
        double totalFines = 0.0;
        for (Transaction transaction : transactions) {
            totalFines += transaction.calculateFine();
        }
        return totalFines;
    }
}
