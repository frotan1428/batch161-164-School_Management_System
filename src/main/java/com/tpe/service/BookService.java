package com.tpe.service;

import com.tpe.domain.Book;
import com.tpe.domain.Teacher;
import com.tpe.dto.BookDto;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.BookRepository;
import com.tpe.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookById(String id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Book is not found with id: " + id));
    }

    public void deleteBookById(String id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Page<Book> getAllBooksWithPage(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book updateBookById(String id, BookDto bookDto) {
        Book existingBook = getBookById(id);

        existingBook.setTitle(bookDto.getTitle());
        existingBook.setAuthor(bookDto.getAuthor());
        existingBook.setPublicationDate(bookDto.getPublicationDate());

        return bookRepository.save(existingBook);
    }

    public List<Book> getBookByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthor(author);

        if (books.isEmpty()) {
            throw new ResourceNotFoundException("No books found with author: " + author);
        }
        return books;
    }

    @Transactional
    public ResponseEntity<Map<String, String>> addBookForTeacher(String teacherId, String bookId) {

        // Step 1: Find teacher by id
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
        if (optionalTeacher.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Teacher with id " + teacherId + " does not exist");
            response.put("success", "false");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404 (NOT FOUND)
        }

        Teacher teacher = optionalTeacher.get();

        // Step 2: Find book by id
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Book with id " + bookId + " does not exist");
            response.put("success", "false");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404 (NOT FOUND)
        }

        Book book = optionalBook.get();

        // Step 3: Check if the book already exists for the teacher
        boolean bookExist = teacher.getBooks().stream().anyMatch(b -> b.getId().equals(book.getId()));
        if (bookExist) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Book already exists for the teacher with id " + teacherId);
            response.put("success", "false");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400 (BAD REQUEST)
        }

        // Step 4: Add the book to the teacher and save the teacher
        teacher.getBooks().add(book);
        teacherRepository.save(teacher);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Book with id " + bookId + " has been added for the Teacher with id: " + teacherId);
        response.put("success", "true");
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 (CREATED)
    }

}
