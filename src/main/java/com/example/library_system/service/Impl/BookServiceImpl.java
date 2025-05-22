package com.example.library_system.service.Impl;

import com.example.library_system.dto.BookDTO;
import com.example.library_system.entity.Book;
import com.example.library_system.mapper.BookMapper;
import com.example.library_system.repository.BookRepository;
import com.example.library_system.service.BookService;
import com.example.library_system.utill.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    //save book
    @Override
    public ApiResponse save(BookDTO bookDTO) {
        try{
            bookRepository.save(bookMapper.EntityMapDto(bookDTO));
            return new ApiResponse(true,"Book saved successfully", new Date());
        }catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Duplicate ISBN: " + bookDTO.getIsbn());
        }
        catch (Exception e){
            return new ApiResponse( false,"Book not saved Error : "+e.getMessage(), new Date());
        }
    }

    @Override
    public List<BookDTO> findByAll() {
        return bookMapper.EntityToDtoList(bookRepository.findAll());
    }
}
