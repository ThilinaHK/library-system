package com.example.library_system.service.Impl;

import com.example.library_system.dto.BookDTO;
import com.example.library_system.entity.Book;
import com.example.library_system.mapper.BookMapper;
import com.example.library_system.repository.BookRepository;
import com.example.library_system.utill.ApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void save() {
        BookDTO bookDTO = new BookDTO(1, "123456789", "Effective Java", "Joshua Bloch", false);
        Book book = new Book(1, "123456789", "Effective Java", "Joshua Bloch", false);

        when(bookMapper.EntityMapDto(bookDTO)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);

        when(bookMapper.EntityMapDto(bookDTO)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);

        ApiResponse response = bookService.save(bookDTO);

        assertTrue(response.isSuccess());
        assertEquals("Book saved successfully", response.getMessage());
    }

    @Test
    void findByAll() {
        BookDTO bookDTO = new BookDTO(1, "123456789", "Effective Java", "Joshua Bloch", false);
        Book book = new Book(1, "123456789", "Effective Java", "Joshua Bloch", false);

        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(bookMapper.EntityToDtoList(List.of(book))).thenReturn(List.of(bookDTO));

        when(bookMapper.EntityMapDto(bookDTO)).thenReturn(book);
        doThrow(DataIntegrityViolationException.class).when(bookRepository).save(book);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookService.save(bookDTO);
        });

        assertEquals("Duplicate ISBN: 123456789", exception.getMessage());
    }

    @Test
    void shouldReturnAllBooks() {
        List<Book> books = List.of(
                new Book(1, "111111", "Clean Code", "Robert C. Martin", false),
                new Book(2, "222222", "The Pragmatic Programmer", "Andy Hunt", false)
        );

        List<BookDTO> bookDTOs = List.of(
                new BookDTO(1, "111111", "Clean Code", "Robert C. Martin", false),
                new BookDTO(2, "222222", "The Pragmatic Programmer", "Andy Hunt", false)
        );

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.EntityToDtoList(books)).thenReturn(bookDTOs);

        List<BookDTO> result = bookService.findByAll();

        assertEquals(2, result.size());
        assertEquals("Clean Code", result.get(0).getTitle());
    }
}
