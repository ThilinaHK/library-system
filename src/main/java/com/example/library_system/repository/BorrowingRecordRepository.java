package com.example.library_system.repository;

import com.example.library_system.entity.Book;
import com.example.library_system.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Integer> {

    Optional<BorrowingRecord> findByBook(Book book);
}
