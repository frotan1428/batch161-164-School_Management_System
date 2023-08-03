package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public void saveStudent(Student student) {

      Student existStudent =  studentRepository.findByEmail(student.getEmail());

      if (existStudent!=null){
          throw new ConflictException("Student with this email is already exist ..");
      }
        studentRepository.save(student);
    }

    public List<Student> findAllStudents() {

       List<Student> students= studentRepository.findAll();

       if (students.isEmpty()){
           throw  new ResourceNotFoundException("Student List is Empty ...");
       }
       return students;

    }

    public Student getStudentById(String id) {
        return studentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Student is not found with id : " + id));
    }
}
