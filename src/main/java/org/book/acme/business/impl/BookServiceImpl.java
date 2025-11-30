package org.book.acme.business.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.book.acme.business.BookService;
import org.book.acme.dao.entity.BookEntity;
import org.book.acme.dao.repository.BookRepository;
import org.book.acme.expose.dto.BookDto;
import org.book.acme.mapper.BookMapper;
import org.book.acme.utils.ErrorMessages;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class BookServiceImpl implements BookService {

    @Inject
    BookRepository bookRepository;

    @Inject
    BookMapper bookMapper;


    @Override
    public List<BookDto.Response> getBooks() {
        return bookMapper.toResponseList(bookRepository.getAllBooks());
    }

    @Override
    public BookDto.Response getBook(UUID id) {
        return bookRepository.getById(id).map(bookMapper::toResponse)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                ErrorMessages.entityNotFound("Book", id)
                        ));
    }

    @Override
    @Transactional
    public BookDto.Response createBook(BookDto.Request request) {
        BookEntity bookEntity = bookMapper.toEntity(request);
        bookRepository.persist(bookEntity);
        return bookMapper.toResponse(bookEntity);
    }

    @Override
    @Transactional
    public BookDto.Response updateBook(UUID id, BookDto.Request request) {
        BookEntity bookEntity = bookRepository.getByIdOptionalIsActive(id)
                .orElseThrow(()-> new EntityNotFoundException(ErrorMessages.entityNotFound("Book", id)));
        bookMapper.updateBookEntityFromRequest(request, bookEntity);
        bookRepository.persist(bookEntity);
        return bookMapper.toResponse(bookEntity);
    }

    @Override
    @Transactional
    public void deleteBook(UUID id) {
        BookEntity bookEntity = bookRepository.findByIdOptional(id).orElseThrow(()-> new EntityNotFoundException(ErrorMessages.entityNotFound("Book", id)));
        bookEntity.setIsActive(false);
    }
}
