package com.tpe.repository;

import com.tpe.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);

}
