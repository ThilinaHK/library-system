package com.example.library_system.service.Impl;

import com.example.library_system.dto.BookDTO;
import com.example.library_system.entity.Book;
import com.example.library_system.mapper.BookMapper;
import com.example.library_system.repository.BookRepository;
import com.example.library_system.utill.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookDTO bookDTO;
    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bookDTO = new BookDTO();
        bookDTO.setIsbn("123");
        bookDTO.setTitle("Land");

        book = new Book();
        book.setIsbn("123");
        book.setTitle("Land");
    }

//    @Test
//    void save() {
//    }

    @Test
    void testSave_Success() {
        when(bookMapper.EntityMapDto(bookDTO)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        ApiResponse response = bookService.save(bookDTO);

        assertTrue(response.isSuccess());
        assertEquals("Book saved successfully", response.getMessage());
        assertNotNull(response.getTimestamp());

        verify(bookMapper).EntityMapDto(bookDTO);
        verify(bookRepository).save(book);
    }

    @Test
    void testSave_DuplicateISBN_ThrowsException() {
        when(bookMapper.EntityMapDto(bookDTO)).thenReturn(book);
        doThrow(new DataIntegrityViolationException("Duplicate")).when(bookRepository).save(book);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookService.save(bookDTO);
        });

        assertEquals("Duplicate ISBN: 123", exception.getMessage());

        verify(bookMapper).EntityMapDto(bookDTO);
        verify(bookRepository).save(book);
    }

    @Test
    void testSave_OtherException_ReturnsErrorResponse() {
        when(bookMapper.EntityMapDto(bookDTO)).thenReturn(book);
        doThrow(new RuntimeException("DB error")).when(bookRepository).save(book);

        ApiResponse response = bookService.save(bookDTO);

        assertFalse(response.isSuccess());
        assertTrue(response.getMessage().contains("Book not saved Error : DB error"));
        assertNotNull(response.getTimestamp());

        verify(bookMapper).EntityMapDto(bookDTO);
        verify(bookRepository).save(book);
    }

    @Test
    void findByAll() {
    }

    @Test
    void testFindByAll_ReturnsMappedList() {
        List<Book> bookList = Arrays.asList(book);
        List<BookDTO> dtoList = Arrays.asList(bookDTO);

        when(bookRepository.findAll()).thenReturn(bookList);
        when(bookMapper.EntityToDtoList(bookList)).thenReturn(dtoList);

        List<BookDTO> result = bookService.findByAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("123", result.get(0).getIsbn());
        assertEquals("Land", result.get(0).getTitle());

        verify(bookRepository, times(1)).findAll();
        verify(bookMapper, times(1)).EntityToDtoList(bookList);
    }
}
