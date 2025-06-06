package com.example.library_system.service.Impl;

import com.example.library_system.dto.BorrowerDTO;
import com.example.library_system.entity.Book;
import com.example.library_system.entity.Borrower;
import com.example.library_system.entity.BorrowingRecord;
import com.example.library_system.exceptions.custom.BookServiceExceptions;
import com.example.library_system.mapper.BorrowerMapper;
import com.example.library_system.repository.BookRepository;
import com.example.library_system.repository.BorrowerRepository;
import com.example.library_system.repository.BorrowingRecordRepository;
import com.example.library_system.service.BookService;
import com.example.library_system.service.BorrowerService;
import com.example.library_system.service.BorrowingRecordService;
import com.example.library_system.utill.common.ApiResponse;
import com.example.library_system.utill.enums.BorrowType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BorrowerServiceImpl implements BorrowerService {

    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;
    private final BorrowerMapper borrowerMapper;
    private final BorrowingRecordService borrowingRecordService;

    @Override
    public ApiResponse save(BorrowerDTO borrowerDTO) {
        borrowerRepository.save(borrowerMapper.EntityMapDto(borrowerDTO));
        return new ApiResponse(true, "Borrower saved successfully",new Date());
    }

    @Override
    public List<BorrowerDTO> findByAll() {
        return borrowerMapper.EntityToDtoList(borrowerRepository.findAll());
    }

    @Override
    public ApiResponse borrowBook(int borrowerId, int bookId) {
        Borrower borrower = borrowerRepository.findById(borrowerId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();
        if (book.isBorrowed()) {
            throw new BookServiceExceptions("Book already borrowed.");
        }
        book.setBorrowed(true);
        bookRepository.save(book);
        borrowingRecordService.save(mapToBorrowingRecord(book, borrower), BorrowType.BORROW);
        return new ApiResponse(true, "Borrowed successfully",new Date());
    }

    @Override
    public ApiResponse returnBook(int borrowerId, int bookId) {
        Borrower borrower = borrowerRepository.findById(borrowerId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        if (!book.isBorrowed()) {
            throw new BookServiceExceptions("Book is not borrowed.");
        }
        book.setBorrowed(false);
        bookRepository.save(book);
        borrowingRecordService.save(mapToBorrowingRecord(book, borrower),BorrowType.RETURN);
        return new ApiResponse(true, "Borrowed Return Successfully",new Date());
    }

    public BorrowingRecord mapToBorrowingRecord(Book book,Borrower borrower){
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setBorrower(borrower);
        return borrowingRecord;
    }
}
