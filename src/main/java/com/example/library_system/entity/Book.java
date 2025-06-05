package com.example.library_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    @NotBlank(message = "ISBN is required")
    private String isbn;
    @NotBlank(message = "Title is required")
    @Column(unique = true)
    private String title;
    @NotBlank(message = "Author is required")
    @Column(unique = true)
    private String author;
    private boolean borrowed = false;
}
