package com.example.library_system.exceptions.global;

import com.example.library_system.exceptions.custom.BookServiceExceptions;
import com.example.library_system.utill.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final boolean FLASE = false;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleIllegalArgument(Exception ex) {
        if( ex instanceof Exception){
            return new ResponseEntity<>(new ApiResponse(FLASE,ex.getMessage(),new Date()), HttpStatus.INTERNAL_SERVER_ERROR);
        }else if(ex instanceof BookServiceExceptions){
            return new ResponseEntity<>(new ApiResponse(FLASE,ex.getMessage(),new Date()), HttpStatus.CONFLICT);
        }else{
            return new ResponseEntity<>(new ApiResponse(FLASE,ex.getMessage(),new Date()), HttpStatus.BAD_REQUEST);
        }
    }

}
