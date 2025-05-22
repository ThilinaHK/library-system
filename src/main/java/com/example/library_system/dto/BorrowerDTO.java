package com.example.library_system.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class BorrowerDTO {
    private int id;
    private String name;
    private String email;
}
