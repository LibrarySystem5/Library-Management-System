package com.library.management.repository;

import com.library.management.model.Transaction;
import com.library.management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByStudent(Student student);
    List<Transaction> findByStudentAndReturned(Student student, boolean returned);
    List<Transaction> findByReturned(boolean returned);
}
