package com.tpe.controller;


import com.tpe.domain.Student;
import com.tpe.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/students")//http://localhost:8080/students
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<Map<String,String>> saveStudent(@Valid  @RequestBody Student student){

        studentService.saveStudent(student);

        Map<String,String> response= new HashMap<>();
        response.put("message","Student has been saved successfully..");
        response.put("status","true");

        return  new ResponseEntity<>(response, HttpStatus.CREATED);// 201(CREATED)

    }

}
