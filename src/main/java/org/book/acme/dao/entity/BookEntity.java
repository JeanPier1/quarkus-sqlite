package org.book.acme.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.book.acme.config.Auditable;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "books")
public class BookEntity  extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "BookId")
    private UUID id;


    @Column(name = "Title")
    private String title;

    @Column(name = "Author")
    private String  author;

    @Column(name = "Amount")
    private Integer amount;

}
