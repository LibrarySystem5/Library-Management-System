package com.library.management.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@DiscriminatorValue("LIBRARIAN")
public class Librarian extends User {
    
    public Librarian(String name, String email, String password) {
        super(null, name, email, password, "LIBRARIAN");
    }
    
    @Override
    public void register() {
        System.out.println("Librarian registered: " + getName());
    }
    
    @Override
    public boolean login(String email, String password) {
        return this.getEmail().equals(email) && this.getPassword().equals(password);
    }
    
    @Override
    public void viewBooks(List<Book> books) {
        System.out.println("Librarian viewing " + books.size() + " books");
    }
    
    public void addBook(Book book) {
        System.out.println("Book added: " + book.getTitle());
    }
    
    public void updateBook(Book book) {
        System.out.println("Book updated: " + book.getTitle());
    }
    
    public void deleteBook(Book book) {
        System.out.println("Book deleted: " + book.getTitle());
    }
    
    public void manageInventory(List<Book> books) {
        System.out.println("Managing inventory of " + books.size() + " books");
    }
    
    public void viewAllUsers(List<User> users) {
        System.out.println("Viewing " + users.size() + " users");
    }
    
    public double viewAllFines(List<Transaction> transactions) {
        double totalFines = 0.0;
        for (Transaction transaction : transactions) {
            totalFines += transaction.calculateFine();
        }
        return totalFines;
    }
}
