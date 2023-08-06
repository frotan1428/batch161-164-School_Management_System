package com.tpe.controller;


import com.tpe.domain.Student;
import com.tpe.dto.StudentDto;
import com.tpe.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    @GetMapping////http://localhost:8080/students
    public ResponseEntity<List<Student>> getAllStudents(){
       List<Student> students= studentService.findAllStudents();
       return new ResponseEntity<>(students,HttpStatus.OK);
    }


    @GetMapping("/{id}")//http://localhost:8080/students/64caa0015212500004ddd460
    public ResponseEntity<Student> getStudentById(@PathVariable("id") String id){
       Student student = studentService.getStudentById(id);
       return  new ResponseEntity<>(student,HttpStatus.OK);
    }

    @GetMapping("/query")//http://localhost:8080/students/query?id=64caa0015212500004ddd460
    public ResponseEntity<Student> getStudentByIdWithParam(@RequestParam("id") String id){
        Student student = studentService.getStudentById(id);
        return  new ResponseEntity<>(student,HttpStatus.OK);
    }

    @GetMapping("/name")//http://localhost:8080/students/name?name=tuba

    public ResponseEntity<List<Student>> getStudentByName(@RequestParam String name){

       List<Student> students = studentService.getStudentByName(name);

       return  new ResponseEntity<>(students,HttpStatus.OK);

    }

    @PutMapping("/{studentId}")//http://localhost:8080/students/64caa0015212500004ddd460
    public ResponseEntity<Map<String,String>> updateStudent(@PathVariable String studentId,
                                                            @Valid  @RequestBody StudentDto studentDto)
    {
        studentService.updateStudentById(studentId,studentDto);
        Map<String,String> response= new HashMap<>();
        response.put("message","Student has been updated successfully..");
        response.put("status","true");

        return  new ResponseEntity<>(response, HttpStatus.OK);// 200(OK)

    }

    @DeleteMapping("/{id}")//http://localhost:8080/students/64caa0015212500004ddd460
    public  ResponseEntity<String>   deleteStudentById(@PathVariable  String id){
        studentService.deleteStudentById(id);
        String message="Student with id has been deleted ";
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
























}
