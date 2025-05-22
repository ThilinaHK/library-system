package com.example.library_system.mapper;

import com.example.library_system.dto.BookDTO;
import com.example.library_system.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    Book EntityMapDto(BookDTO bookDTO);

    BookDTO DtoMapEntity(Book book);

    List<BookDTO> EntityToDtoList(List<Book> all);
}
