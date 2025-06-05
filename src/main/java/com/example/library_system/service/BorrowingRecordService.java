package com.example.library_system.service;

import com.example.library_system.entity.BorrowingRecord;
import com.example.library_system.utill.common.ApiResponse;
import com.example.library_system.utill.enums.BorrowType;

public interface BorrowingRecordService {

    ApiResponse save (BorrowingRecord borrowingRecord, BorrowType borrowType);
}
