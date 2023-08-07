package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDto;
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

    public List<Student> getStudentByName(String name) {

       List<Student> students = studentRepository.findByName(name);

       if (students.isEmpty()){
           throw new ResourceNotFoundException("No Students found with name : " +name);//serhan
       }
       return students;

    }

    public void updateStudentById(String studentId, StudentDto studentDto) {

        //check exist students

     Student existStudent =   getStudentById(studentId);//zia@gmail.com -zia@gmail.com



     if (!existStudent.getEmail().equals(studentDto.getEmail())){

       Student studentWithUpdateEmail =  studentRepository.findByEmail(studentDto.getEmail());
       if (studentWithUpdateEmail!=null){
           throw new ConflictException("Email is already exist or it use for another Students.");
       }
     }

     //update operation

        existStudent.setName(studentDto.getName());
        existStudent.setLastName(studentDto.getLastName());
        existStudent.setEmail(studentDto.getEmail());
        existStudent.setPhoneNumber(studentDto.getPhoneNumber());
        studentRepository.save(existStudent);



    }

    public void deleteStudentById(String id) {
         Student student= getStudentById(id);
         studentRepository.delete(student);//3
    }

    public List<Student> findStudentByNameUsingQuery(String lastName) {
        return studentRepository.findStudentByLastNameWithQuery(lastName);
    }

    public List<Student> findStudentWithNullPhoneANdAddress() {
        return studentRepository.findStudentWithNullAddressandPhoneNumber();

    }

    public List<Student> findStudensNameWStartWithVowel() {
        return studentRepository.findStudentsWhoseNameStartWithVowelLetter();
    }
}
