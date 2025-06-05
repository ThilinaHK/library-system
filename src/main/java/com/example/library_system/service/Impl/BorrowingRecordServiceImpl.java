package com.example.library_system.service.Impl;

import com.example.library_system.entity.BorrowingRecord;
import com.example.library_system.repository.BorrowingRecordRepository;
import com.example.library_system.service.BorrowingRecordService;
import com.example.library_system.utill.common.ApiResponse;
import com.example.library_system.utill.enums.BorrowType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;

    @Override
    public ApiResponse save(BorrowingRecord borrowingRecord,BorrowType borrowType) {
        log.info("Saving borrowing record: {}", borrowingRecord);
        try{
            if (BorrowType.RETURN.equals(borrowType)) {
                borrowingRecord.setReturnedAt(LocalDateTime.now());
                borrowingRecord.setBorrowType(borrowType);
                borrowingRecordRepository.save(borrowingRecord);
                return new ApiResponse(false, "Book Returning Done", new Date());
            }else{
                borrowingRecord.setBorrowType(borrowType);
                borrowingRecordRepository.save(borrowingRecord);
                return new ApiResponse(true, "Save borrowing record: ", new Date());
            }
        }catch (Exception e){
            return new ApiResponse(false, "Failed to save borrowing record: " + e.getMessage(), new Date());
        }
    }
}
