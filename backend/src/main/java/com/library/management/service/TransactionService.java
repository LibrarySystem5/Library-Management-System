package com.library.management.service;

import com.library.management.model.Book;
import com.library.management.model.Student;
import com.library.management.model.Transaction;
import com.library.management.model.User;
import com.library.management.repository.BookRepository;
import com.library.management.repository.TransactionRepository;
import com.library.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Transaction borrowBook(Long studentId, Long bookId) {
        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        if (!(user instanceof Student)) {
            throw new RuntimeException("Only students can borrow books");
        }
        
        Student student = (Student) user;
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        
        if (!book.isAvailable()) {
            throw new RuntimeException("Book is not available");
        }
        
        student.borrowBook(book);
        book.updateStatus(false);
        bookRepository.save(book);
        
        Transaction transaction = new Transaction(student, book, LocalDate.now());
        transaction.recordBorrow();
        return transactionRepository.save(transaction);
    }
    
    public Transaction returnBook(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        
        if (transaction.isReturned()) {
            throw new RuntimeException("Book already returned");
        }
        
        Book book = transaction.getBook();
        Student student = transaction.getStudent();
        
        student.returnBook(book, transaction);
        book.updateStatus(true);
        bookRepository.save(book);
        
        transaction.recordReturn();
        return transactionRepository.save(transaction);
    }
    
    public List<Transaction> getStudentTransactions(Long studentId) {
        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        if (!(user instanceof Student)) {
            throw new RuntimeException("Invalid user type");
        }
        
        return transactionRepository.findByStudent((Student) user);
    }
    
    public List<Transaction> getActiveTransactions(Long studentId) {
        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        if (!(user instanceof Student)) {
            throw new RuntimeException("Invalid user type");
        }
        
        return transactionRepository.findByStudentAndReturned((Student) user, false);
    }
    
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    
    public List<Transaction> getAllActiveTransactions() {
        return transactionRepository.findByReturned(false);
    }
    
    public double calculateStudentFines(Long studentId) {
        List<Transaction> transactions = getStudentTransactions(studentId);
        double totalFines = 0.0;
        for (Transaction transaction : transactions) {
            totalFines += transaction.calculateFine();
        }
        return totalFines;
    }
    
    public double calculateAllFines() {
        List<Transaction> transactions = getAllTransactions();
        double totalFines = 0.0;
        for (Transaction transaction : transactions) {
            totalFines += transaction.calculateFine();
        }
        return totalFines;
    }
}
