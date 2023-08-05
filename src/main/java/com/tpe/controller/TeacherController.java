package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.domain.Teacher;
import com.tpe.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teachers")//http://localhost:8080/teachers
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping////http://localhost:8080/teachers
    public ResponseEntity<Map<String,String>> saveTeacher(@Valid @RequestBody Teacher teacher){

        teacherService.saveTeacher(teacher);

        Map<String,String> response= new HashMap<>();
        response.put("message","Teacher has been saved successfully..");
        response.put("status","true");

        return  new ResponseEntity<>(response, HttpStatus.CREATED);// 201(CREATED)
    }
    @GetMapping // http://localhost:8080/teachers
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.findAllTeachers();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}") // http://localhost:8080/teachers/
    public ResponseEntity<String> deleteTeacherById(@PathVariable String id) {
        teacherService.deleteTeacherById(id);
        return new ResponseEntity<>("Teacher with ID " + id + " has been deleted.", HttpStatus.OK);
    }

    @GetMapping("/{id}") // http://localhost:8080/teachers/64c793b61b60301ec68db2eb
    public ResponseEntity<Teacher> getTeacherById(@PathVariable String id) {
        Teacher teacher = teacherService.getTeacherById(id);
        return new ResponseEntity<>(teacher, HttpStatus.OK);
    }

    @GetMapping("/query") // http://localhost:8080/teachers/query?id=64c793b61b60301ec68db2eb
    public ResponseEntity<Teacher> getTeacherUsingParam(@RequestParam("id") String id) {
        Teacher teacher = teacherService.getTeacherById(id);
        return new ResponseEntity<>(teacher, HttpStatus.OK);
    }

    @GetMapping("/byLastName") // http://localhost:8080/teachers/byLastName?lastName=Muslih
    public ResponseEntity<List<Teacher>> getTeacherByLastName(@RequestParam String lastName) {
        List<Teacher> teachers = teacherService.getTeacherByLastName(lastName);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }






}
