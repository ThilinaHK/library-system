package com.example.library_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDTO {
    private int id;
    private String isbn;
    private String title;
    private String author;
    private boolean borrowed = false;
}
