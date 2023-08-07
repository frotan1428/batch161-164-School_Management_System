package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDto;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student saveStudent(Student student) {
        // Check if a student with the same email already exists
        Student existingStudent = studentRepository.findByEmail(student.getEmail());
        if (existingStudent != null) {
            // An existing student with the same email already exists
            throw new ConflictException("Student with the same email already exists.");
        }

        // If no existing student with the same email, save the new student to the database
        return studentRepository.save(student);
    }

    public Student getStudentById(String id) {
        return studentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Student is not found with id : " + id));
    }

    public void deleteStudentById(String id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }

    public List<Student> findAllStudents() {
        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            throw new ResourceNotFoundException("Student List is empty : ");
        }
        return students;
    }

    public List<Student> getStudentByLastName(String lastName) {
        return studentRepository.findByLastName(lastName);
    }

    public void updateStudentById(String id, StudentDto studentDto) {
        // Get the existing student from the database by ID
        Student existingStudent = getStudentById(id);

        // Check if the email being updated already exists for any other student
        if (!existingStudent.getEmail().equals(studentDto.getEmail())) {
            Student studentWithUpdatedEmail = studentRepository.findByEmail(studentDto.getEmail());
            if (studentWithUpdatedEmail != null) {
                throw new ConflictException("Email already exists for another student.");
            }
        }

        // Update the fields of the existing student with the new values
        existingStudent.setName(studentDto.getName());
        existingStudent.setLastName(studentDto.getLastName());
        existingStudent.setEmail(studentDto.getEmail());
        existingStudent.setPhoneNumber(studentDto.getPhoneNumber());

        // Save the updated student to the database
        studentRepository.save(existingStudent);
    }

    public List<Student> getStudentByName(String name) {
        List<Student> students = studentRepository.findByName(name);
        if (students.isEmpty()) {
            throw new ResourceNotFoundException("No students found with name: " + name);
        }
        return students;
    }

    public Page<Student> getAllStudentsWithPage(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }



    public StudentDto getStudentDTOById(String id) {
        return studentRepository.findStudentByDto(id).orElseThrow(() ->
                new ResourceNotFoundException("Student with ID " + id + " not found"));
    }


    public List<Student> findStudentsWithNullPhoneNumber() {
        return studentRepository.findStudentsWithNullPhoneNumber();
    }
    public List<Student> findStudentsWhoseNamesStartWithVowel() {
        return studentRepository.findStudentsWhoseNamesStartWithVowel();
    }


    public List<Student> findStudentsByLastName(String lastName) {
        return studentRepository.findStudentsByLastName(lastName);
    }


}
