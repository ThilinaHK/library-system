package com.example.library_system.controller;

import com.example.library_system.dto.BorrowerDTO;
import com.example.library_system.service.BorrowerService;
import com.example.library_system.utill.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/borrower")
@AllArgsConstructor
public class BorrowerController {
    private final BorrowerService borrowerService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody BorrowerDTO borrowerDTO) {
        return ResponseEntity.ok(borrowerService.save(borrowerDTO));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<BorrowerDTO>> findAll() {
        return new ResponseEntity<>(borrowerService.findByAll(), HttpStatus.OK);
    }

    @PostMapping("/borrow/{borrowerId}/{bookId}")
    public ResponseEntity<?> borrowBook(@PathVariable ("borrowerId") int borrowerId,@PathVariable ("bookId") int bookId) {
        return new ResponseEntity<>(borrowerService.borrowBook(borrowerId, bookId).getMessage(), HttpStatus.OK);
    }

    @PostMapping("/returnBorrow/{borrowerId}/{bookId}")
    public ResponseEntity<?> returnBook(@PathVariable int borrowerId,@PathVariable int bookId) {
        return new ResponseEntity<>(borrowerService.returnBook(borrowerId, bookId).getMessage(), HttpStatus.OK);
    }
}
