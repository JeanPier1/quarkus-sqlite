package org.book.acme.mapper;



import org.book.acme.dao.entity.BookEntity;
import org.book.acme.expose.dto.BookDto;
import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface BookMapper {

    BookDto.Response toResponse(BookEntity bookEntity);

    BookEntity toEntity(BookDto.Request request);

    void updateBookEntityFromRequest(BookDto.Request request, @MappingTarget BookEntity bookEntity);

    List<BookDto.Response> toResponseList(List<BookEntity> bookEntities);
}
