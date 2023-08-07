package com.tpe.controller;

import com.tpe.domain.Teacher;
import com.tpe.dto.TeacherDto;
import com.tpe.service.TeacherService;
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
@RequestMapping("/teachers") // http://localhost:8080/teachers
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /*
    {
    "name" : "busra",
    "lastName" :"linda",
    "email" :"busra@gmail.com",
    "phoneNumber":"222222"
}
     */

    @PostMapping // http://localhost:8080/teachers/64c793b61b60301ec68db2eb
    public ResponseEntity<Map<String, String>> saveTeacher(@Valid @RequestBody Teacher teacher) {
        Teacher savedTeacher = teacherService.saveTeacher(teacher);

        Map<String, String> map = new HashMap<>();
        map.put("message", "Teacher has been saved successfully.");
        map.put("success", "true");

        return new ResponseEntity<>(map, HttpStatus.CREATED); // HttpStatus: 201 (CREATED)
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



    /*
    {
        "name" :"frotan2",
            "lastName" :"asra",
            "email" :"frotan@gmail.com",
            "phoneNumber":"456789"
    }
    */
    @PutMapping("/{teacherId}") // http://localhost:8080/teachers/64c793b61b60301ec68db2eb
    public ResponseEntity<Map<String, String>> updateTeacher(
            @PathVariable String teacherId,
            @Valid @RequestBody TeacherDto teacherDto
    ) {
        teacherService.updateTeacherById(teacherId, teacherDto);

        // Return a success response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Teacher with ID " + teacherId + " has been updated successfully.");
        response.put("success", "true");
        return new ResponseEntity<>(response, HttpStatus.OK); // HttpStatus: 200 (OK)
    }

    @GetMapping("/page") // http://localhost:8080/teachers/page?page=1&size=1&sort=name&direction=ASC
    public ResponseEntity<Page<Teacher>> getTeachersByPage(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop,
            @RequestParam("direction") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<Teacher> pageOfTeachers = teacherService.getAllTeachersWithPage(pageable);
        return ResponseEntity.ok(pageOfTeachers);
    }

    @GetMapping("/name") // http://localhost:8080/teachers/name?name=john
    public ResponseEntity<List<Teacher>> getTeacherByNameUsingJPQL(@RequestParam String name) {
        List<Teacher> teachers = teacherService.getTeacherByName(name);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @GetMapping("/query/dto") // http://localhost:8080/teachers/query/dto?id=64c793b61b60301ec68db2eb + GET
    public ResponseEntity<TeacherDto> getTeacherDTO(@RequestParam String id) {
        TeacherDto teacherDTO = teacherService.getTeacherDTOById(id);
        return ResponseEntity.ok(teacherDTO);
    }



}
