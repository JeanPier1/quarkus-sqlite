package org.book.acme.dao.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.book.acme.config.BaseRepository;
import org.book.acme.dao.entity.BookEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class BookRepository extends BaseRepository<BookEntity> {

    public List<BookEntity> getAllBooks() {
        return find("isActive=true").list();
    }

    public Optional<BookEntity> getByIdOptionalIsActive(UUID id) {
        return find("id = ?1 and isActive=true", id).firstResultOptional();
    }
}
