package com.tpe.controller;

import com.tpe.domain.Book;
import com.tpe.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/books")//http://localhost:8080/books
public class BookController {

    @Autowired
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping///http://localhost:8080/books

    public ResponseEntity<Map<String,String>> saveBook(@Valid @RequestBody Book book){

        bookService.saveBook(book);
        Map<String,String> response= new HashMap<>();
        response.put("message","Book has been saved successfully..");
        response.put("status","true");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")//http://localhost:8080/books/64cfdab28224844b27654a7f
    public ResponseEntity<Book> getBookById(@PathVariable String id){
       Book book= bookService.getBookById(id);

        return new ResponseEntity<>(book,HttpStatus.OK);
    }


    @PutMapping("/{teacherId}/books")//http://localhost:8080/books/{teacherId}/books?BookId={bookId}

    public ResponseEntity<Map<String,String>> addBookForTeacher(
            @PathVariable String teacherId,
            @RequestParam String bookId
    ){
        return bookService.addBookForTeacher(teacherId,bookId);
    }










}
