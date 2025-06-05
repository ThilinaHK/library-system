package com.example.library_system.controller;

import com.example.library_system.dto.BookDTO;
import com.example.library_system.service.BookService;
import com.example.library_system.utill.common.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.save(bookDTO));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<BookDTO>> findAll() {
        return new ResponseEntity<>(bookService.findByAll(), HttpStatus.OK);
    }
}
