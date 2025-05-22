package com.example.library_system.repository;

import com.example.library_system.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Integer> {

    Optional<Book> findByIsbn(String isbn);

}
