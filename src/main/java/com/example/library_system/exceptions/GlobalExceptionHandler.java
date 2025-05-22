package com.example.library_system.exceptions;

import com.example.library_system.utill.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.crypto.Data;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleIllegalArgument(Exception ex) {
        if( ex instanceof Exception){
            return new ResponseEntity<>(new ApiResponse(false,ex.getMessage(),new Date()), HttpStatus.INTERNAL_SERVER_ERROR);
        }else if(ex instanceof BookServiceExceptions){
            return new ResponseEntity<>(new ApiResponse(false,ex.getMessage(),new Date()), HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            return new ResponseEntity<>(new ApiResponse(false,ex.getMessage(),new Date()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
