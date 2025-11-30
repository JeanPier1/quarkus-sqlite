package org.book.acme.business;

import org.book.acme.expose.dto.BookDto;

import java.util.List;
import java.util.UUID;

public interface BookService {

    List<BookDto.Response> getBooks();
    BookDto.Response getBook(UUID id);
    BookDto.Response createBook(BookDto.Request request);
    BookDto.Response updateBook(UUID id, BookDto.Request request);
    void deleteBook(UUID id);
}
