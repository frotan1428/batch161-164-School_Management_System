package com.tpe.service;

import com.tpe.domain.Book;
import com.tpe.domain.Teacher;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.BookRepository;
import com.tpe.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Autowired
    private TeacherRepository teacherRepository;

    public Book getBookById(String id) {

        return bookRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Book is not found with id : " +id));

    }

    public ResponseEntity<Map<String, String>> addBookForTeacher(String teacherId, String bookId) {

        //step1 find Teacher by id:
       Optional<Teacher>  optionalTeacher = teacherRepository.findById(teacherId);
       if (optionalTeacher.isEmpty()){

           Map<String,String> response= new HashMap<>();
           response.put("message","Teacher  with id "+ teacherId + " does not exist");
           response.put("status","true");

           return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
       }
      Teacher teacher = optionalTeacher.get();

        //step2 find book by id:

       Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isEmpty()){

            Map<String,String> response= new HashMap<>();
            response.put("message","Book  with id "+ bookId + " does not exist");
            response.put("status","true");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

         Book book = optionalBook.get();
        //step 3 check the book is exist for another teacher

        boolean bookExist= teacher.getBooks().stream().anyMatch(b-> b.getId().equals(book.getId()));

        if (bookExist){

            Map<String,String> response= new HashMap<>();
            response.put("message","Book  is already exist   for another teacher  with Name : " +teacher.getName());
            response.put("status","true");

            return new ResponseEntity<>(response,HttpStatus.CONFLICT);
        }
        //step 4: add the book to the teacher and save the teacher

        teacher.getBooks().add(book);
        teacherRepository.save(teacher);

        Map<String,String> response= new HashMap<>();
        response.put("message","Book is added Successfully ...");
        response.put("status","true");

        return new ResponseEntity<>(response,HttpStatus.CREATED);


    }
}
