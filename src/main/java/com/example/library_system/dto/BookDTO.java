package com.example.library_system.dto;

import lombok.Data;

@Data
public class BookDTO {
    private int id;
    private String isbn;
    private String title;
    private String author;
    private boolean borrowed = false;
}
