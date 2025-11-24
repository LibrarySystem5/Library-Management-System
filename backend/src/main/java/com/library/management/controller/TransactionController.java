package com.library.management.controller;

import com.library.management.model.Transaction;
import com.library.management.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
    @PostMapping("/borrow")
    public ResponseEntity<Transaction> borrowBook(@RequestBody Map<String, Long> request) {
        try {
            Long studentId = request.get("studentId");
            Long bookId = request.get("bookId");
            Transaction transaction = transactionService.borrowBook(studentId, bookId);
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @PostMapping("/return/{transactionId}")
    public ResponseEntity<Transaction> returnBook(@PathVariable Long transactionId) {
        try {
            Transaction transaction = transactionService.returnBook(transactionId);
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Transaction>> getStudentTransactions(@PathVariable Long studentId) {
        try {
            return ResponseEntity.ok(transactionService.getStudentTransactions(studentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/student/{studentId}/active")
    public ResponseEntity<List<Transaction>> getActiveTransactions(@PathVariable Long studentId) {
        try {
            return ResponseEntity.ok(transactionService.getActiveTransactions(studentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }
    
    @GetMapping("/all/active")
    public ResponseEntity<List<Transaction>> getAllActiveTransactions() {
        return ResponseEntity.ok(transactionService.getAllActiveTransactions());
    }
    
    @GetMapping("/fines/student/{studentId}")
    public ResponseEntity<Map<String, Double>> getStudentFines(@PathVariable Long studentId) {
        try {
            double fines = transactionService.calculateStudentFines(studentId);
            Map<String, Double> response = new HashMap<>();
            response.put("totalFines", fines);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/fines/all")
    public ResponseEntity<Map<String, Double>> getAllFines() {
        double fines = transactionService.calculateAllFines();
        Map<String, Double> response = new HashMap<>();
        response.put("totalFines", fines);
        return ResponseEntity.ok(response);
    }
}
