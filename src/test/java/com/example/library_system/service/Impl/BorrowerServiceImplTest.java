package com.example.library_system.service.Impl;

import com.example.library_system.dto.BorrowerDTO;
import com.example.library_system.entity.Book;
import com.example.library_system.entity.Borrower;
import com.example.library_system.exceptions.BookServiceExceptions;
import com.example.library_system.mapper.BorrowerMapper;
import com.example.library_system.repository.BookRepository;
import com.example.library_system.repository.BorrowerRepository;
import com.example.library_system.utill.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowerServiceImplTest {


    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowerRepository borrowerRepository;

    @Mock
    private BorrowerMapper borrowerMapper;

    @InjectMocks
    private BorrowerServiceImpl borrowerService;

    private BorrowerDTO borrowerDTO;
    private Borrower borrower;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        borrowerDTO = new BorrowerDTO();
        borrowerDTO.setId(1);
        borrowerDTO.setName("Thilina");

        borrower = new Borrower();
        borrower.setId(1);
        borrower.setName("Thilina");
    }

    @Test
    void testSave_Success() {
        when(borrowerMapper.EntityMapDto(borrowerDTO)).thenReturn(borrower);
        when(borrowerRepository.save(borrower)).thenReturn(borrower);

        ApiResponse response = borrowerService.save(borrowerDTO);

        assertTrue(response.isSuccess());
        assertEquals("Borrower saved successfully", response.getMessage());
        assertNotNull(response.getTimestamp());

        verify(borrowerMapper, times(1)).EntityMapDto(borrowerDTO);
        verify(borrowerRepository, times(1)).save(borrower);
    }


    @Test
    void save() {
    }

    @Test
    void findByAll() {
    }

    @Test
    void borrowBook() {
    }

    @Test
    void testFindByAll_ReturnsMappedList() {
        List<Borrower> borrowerList = Arrays.asList(borrower);
        List<BorrowerDTO> dtoList = Arrays.asList(borrowerDTO);

        when(borrowerRepository.findAll()).thenReturn(borrowerList);
        when(borrowerMapper.EntityToDtoList(borrowerList)).thenReturn(dtoList);

        List<BorrowerDTO> result = borrowerService.findByAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Thilina", result.get(0).getName());

        verify(borrowerRepository, times(1)).findAll();
        verify(borrowerMapper, times(1)).EntityToDtoList(borrowerList);
    }


    @Test
    void testBorrowBook_Success() {
        int borrowerId = 1;
        int bookId = 100;

        borrower.setId(borrowerId);
        Book book = new Book();
        book.setId(bookId);
        book.setBorrowed(false);

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        ApiResponse response = borrowerService.borrowBook(borrowerId, bookId);

        assertTrue(response.isSuccess());
        assertEquals("Borrowed successfully", response.getMessage());
        assertTrue(book.isBorrowed());

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testBorrowBook_AlreadyBorrowed_ThrowsException() {
        int borrowerId = 1;
        int bookId = 101;

        Book book = new Book();
        book.setId(bookId);
        book.setBorrowed(true);

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        BookServiceExceptions exception = assertThrows(BookServiceExceptions.class, () -> {
            borrowerService.borrowBook(borrowerId, bookId);
        });

        assertEquals("Book already borrowed.", exception.getMessage());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void testBorrowBook_BorrowerNotFound_ThrowsException() {
        int borrowerId = 1;
        int bookId = 101;

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            borrowerService.borrowBook(borrowerId, bookId);
        });

        verify(bookRepository, never()).findById(anyInt());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void testBorrowBook_BookNotFound_ThrowsException() {
        int borrowerId = 1;
        int bookId = 101;

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            borrowerService.borrowBook(borrowerId, bookId);
        });

        verify(bookRepository, never()).save(any());
    }



    @Test
    void returnBook() {
    }

    @Test
    void testReturnBook_Success() {
        int borrowerId = 1;
        int bookId = 200;

        borrower.setId(borrowerId);
        Book book = new Book();
        book.setId(bookId);
        book.setBorrowed(true);

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        ApiResponse response = borrowerService.returnBook(borrowerId, bookId);

        assertTrue(response.isSuccess());
        assertEquals("Borrowed Return Successfully", response.getMessage());
        assertFalse(book.isBorrowed());

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testReturnBook_NotBorrowed_ThrowsException() {
        int borrowerId = 1;
        int bookId = 1;

        Book book = new Book();
        book.setId(bookId);
        book.setBorrowed(false);

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        BookServiceExceptions exception = assertThrows(BookServiceExceptions.class, () -> {
            borrowerService.returnBook(borrowerId, bookId);
        });

        assertEquals("Book is not borrowed.", exception.getMessage());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void testReturnBook_BorrowerNotFound_ThrowsException() {
        int borrowerId = 1;
        int bookId = 202;

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            borrowerService.returnBook(borrowerId, bookId);
        });

        verify(bookRepository, never()).findById(anyInt());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void testReturnBook_BookNotFound_ThrowsException() {
        int borrowerId = 1;
        int bookId = 203;

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            borrowerService.returnBook(borrowerId, bookId);
        });

        verify(bookRepository, never()).save(any());
    }
}
