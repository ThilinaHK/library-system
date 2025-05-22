package com.example.library_system.service;

import com.example.library_system.dto.BookDTO;
import com.example.library_system.utill.ApiResponse;

import java.util.List;

public interface BookService {

    ApiResponse save(BookDTO bookDTO);

    List<BookDTO> findByAll();

}
