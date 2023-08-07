package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDto;
import com.tpe.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students") // http://localhost:8080/students
public class StudentController {

    @Autowired
    private StudentService studentService;


    /*
    {
    "name" : "busra",
    "lastName" :"linda",
    "email" :"busra@gmail.com",
    "phoneNumber":"222222"
}
     */

    @PostMapping // http://localhost:8080/students
    public ResponseEntity<Map<String, String>> saveStudent(@Valid @RequestBody Student student) {
        Student savedStudent = studentService.saveStudent(student);

        Map<String, String> map = new HashMap<>();
        map.put("message", "Student has been saved successfully.");
        map.put("success", "true");

        return new ResponseEntity<>(map, HttpStatus.CREATED); // HttpStatus: 201 (CREATED)
    }

    @GetMapping // http://localhost:8080/students
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @DeleteMapping("/{id}") // http://localhost:8080/students/64c793b61b60301ec68db2eb
    public ResponseEntity<String> deleteStudentById(@PathVariable String id) {
        studentService.deleteStudentById(id);
        return new ResponseEntity<>("Student with ID " + id + " has been deleted.", HttpStatus.OK);
    }

    @GetMapping("/{id}") // http://localhost:8080/students/64c793b61b60301ec68db2eb
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {
        Student student = studentService.getStudentById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/query") // http://localhost:8080/students/query?id=64c793b61b60301ec68db2eb
    public ResponseEntity<Student> getStudentUsingParam(@RequestParam("id") String id) {
        Student student = studentService.getStudentById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/name") // http://localhost:8080/students/name?name=john
    public ResponseEntity<List<Student>> getStudentByName(@RequestParam String name) {
        List<Student> students = studentService.getStudentByName(name);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }


    @GetMapping("/byLastName") // http://localhost:8080/students/byLastName?lastName=Smith
    public ResponseEntity<List<Student>> getStudentByLastName(@RequestParam String lastName) {
        List<Student> students = studentService.getStudentByLastName(lastName);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }


    /*
    {
        "name" :"frotan2",
            "lastName" :"asra",
            "email" :"frotan@gmail.com",
            "phoneNumber":"456789"
    }
    */
    @PutMapping("/{studentId}") // http://localhost:8080/students/64c793b61b60301ec68db2eb
    public ResponseEntity<Map<String, String>> updateStudent(
            @PathVariable String studentId,
            @Valid @RequestBody StudentDto studentDto
    ) {
        studentService.updateStudentById(studentId, studentDto);

        // Return a success response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student with ID " + studentId + " has been updated successfully.");
        response.put("success", "true");
        return new ResponseEntity<>(response, HttpStatus.OK); // HttpStatus: 200 (OK)
    }

    @GetMapping("/page") // http://localhost:8080/students/page?page=1&size=1&sort=name&direction=ASC
    public ResponseEntity<Page<Student>> getStudentsByPage(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop,
            @RequestParam("direction") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<Student> pageOfStudents = studentService.getAllStudentsWithPage(pageable);
        return ResponseEntity.ok(pageOfStudents);
    }


    //Find students by their last names

    //db.students.find({"last_name": "karaca"})
    @GetMapping("/byLastName/{lastName}")//http://localhost:8081/students/byLastName/noor
    public ResponseEntity<List<Student>> getStudentsByLastName(@PathVariable String lastName) {
        List<Student> students = studentService.findStudentsByLastName(lastName);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }


    //find the students who's  address and phoneNumber is null
    //db.student.find({ "phoneNumber": null });;
    // @Query("{'phoneNumber': null}")

    /*
    db.student.find({
        $and: [
        { phoneNumber: { $eq: null } },
        { address: { $eq: null } }
     ]
    });

     */
    @GetMapping("/phone/null")//http://localhost:8081/students/phone/null
    public ResponseEntity<List<Student>> getStudentsWithNullPhoneNumber() {
        List<Student> students = studentService.findStudentsWithNullPhoneNumber();
        if (students.isEmpty()) {
            // If no students found with null phone numbers, return 404 (NOT_FOUND) status
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // If students found, return the list with 200 (OK) status
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    //Find students whose phone numbers are null or empty:

    // db.students.find({'phoneNumber': {$nin: [null, '']}})

    //Find students whose names start with  vowel:
    ///db.students.find({ name: { $regex: '^[^aeiou]', $options: 'i' } });//for mongoDb
    @GetMapping("/names/start-with-vowel") //http://localhost:8081/students/names/start-with-vowel
    public ResponseEntity<List<Student>> getStudentsWhoseNamesStartWithVowel() {
        List<Student> students = studentService.findStudentsWhoseNamesStartWithVowel();
        if (students.isEmpty()) {
            // If no students found whose names start with a vowel, return 404 (NOT_FOUND) status
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // If students found, return the list with 200 (OK) status
        return new ResponseEntity<>(students, HttpStatus.OK);
    }



    @GetMapping("/query/dto") // http://localhost:8080/students/query/dto?id=64c793b61b60301ec68db2eb + GET
    public ResponseEntity<StudentDto> getStudentDTO(@RequestParam String id) {
        StudentDto studentDTO = studentService.getStudentDTOById(id);
        return ResponseEntity.ok(studentDTO);
    }
}
