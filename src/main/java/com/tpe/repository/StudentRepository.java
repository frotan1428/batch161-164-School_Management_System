package com.tpe.repository;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    List<Student> findByLastName(String lastName);

    List<Student> findByName(String name);

    Student findByEmail(String email);

    @Query("{'_id': ?0}") // MongoDB query to find a student by ID
    Optional<StudentDto> findStudentByDto(String id);




    //@Query("{'phoneNumber': {$in: [null, '']}}")

    @Query("{$and: [{'phoneNumber': null}, {'address': null}]}")
    List<Student> findStudentsWithNullPhoneNumber();



   //@Query("{'name': {$regex: '^[^aeiou]', $options: 'i'}}")
   @Query("{'name': {$regex: '^[aeiou]', $options: 'i'}}")
    List<Student> findStudentsWhoseNamesStartWithVowel();

    @Query("{'lastName': ?0}")
    List<Student> findStudentsByLastName(String lastName);



}
