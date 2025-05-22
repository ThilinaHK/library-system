package com.example.library_system.mapper;

import com.example.library_system.dto.BookDTO;
import com.example.library_system.dto.BorrowerDTO;
import com.example.library_system.entity.Book;
import com.example.library_system.entity.Borrower;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BorrowerMapper {

    Borrower EntityMapDto(BorrowerDTO borrowerDTO);

    BorrowerDTO DtoMapEntity(Borrower borrower);

    List<BorrowerDTO> EntityToDtoList(List<Borrower> all);
}
