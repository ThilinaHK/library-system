package com.example.library_system.service;

import com.example.library_system.dto.BorrowerDTO;
import com.example.library_system.utill.common.ApiResponse;

import java.util.List;

public interface BorrowerService {

    ApiResponse save(BorrowerDTO borrowerDTO);

    List<BorrowerDTO> findByAll();

    ApiResponse borrowBook(int borrowerId, int bookId);

    ApiResponse returnBook(int borrowerId, int bookId);
}
