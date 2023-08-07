package com.tpe.controller;

import com.tpe.domain.Book;
import com.tpe.dto.BookDto;
import com.tpe.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping//http://localhost:8081/books
    public ResponseEntity<Map<String, String>> saveBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);

        Map<String, String> map = new HashMap<>();
        map.put("message", "Book has been saved successfully.");
        map.put("success", "true");

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping////http://localhost:8081/books
    public ResponseEntity<List<Book>> findAllBooks() {
        List<Book> books = bookService.findAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")//http://localhost:8081/books/64c7944b1b60301ec68db2ee
    public ResponseEntity<Book> getBookByIdWithPathVariable(@PathVariable String id) {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")//http://localhost:8081/books/64c7944b1b60301ec68db2ee
    public ResponseEntity<String> deleteBook(@PathVariable String id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>("Book with ID " + id + " has been deleted.", HttpStatus.OK);
    }

    @GetMapping("/query")//http://localhost:8081/books/query?id=64c7944b1b60301ec68db2ee
    public ResponseEntity<Book> getBookUsingParam(@RequestParam("id") String id) {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/byTitle")//localhost:8080/books/byTitle?title=java2023
    public ResponseEntity<List<Book>> getBookByTitle(@RequestParam String title) {
        List<Book> books = bookService.getBookByTitle(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")//http://localhost:8081/books/update/64c7944b1b60301ec68db2ee
    public ResponseEntity<Map<String, String>> updateBook(
            @Valid @PathVariable String id,
            @RequestBody BookDto bookDto
    ) {
        Book updatedBook = bookService.updateBookById(id, bookDto);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Book with ID " + id + " has been updated successfully.");
        response.put("success", "true");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/page")//http://localhost:8081/books/page?page=0&size=1&sort=title&direction=ASC
    public ResponseEntity<Page<Book>> getBooksByPage(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop,
            @RequestParam("direction") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<Book> pageOfBooks = bookService.getAllBooksWithPage(pageable);

        return ResponseEntity.ok(pageOfBooks);
    }


    @GetMapping("/author")//http://localhost:8081/books/author?author=Ali
    public ResponseEntity<List<Book>> getBookByAuthor(@RequestParam String author) {
        List<Book> books = bookService.getBookByAuthor(author);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/{teacherId}/books")//http://localhost:8080/books/{teacherId}/books?bookId={bookId} == T 1  and book 2-3

    public ResponseEntity<Map<String,String>> addBookForTeacher(
            @PathVariable String teacherId,
            @RequestParam String bookId
    ){
        return bookService.addBookForTeacher(teacherId,bookId);
    }



}
