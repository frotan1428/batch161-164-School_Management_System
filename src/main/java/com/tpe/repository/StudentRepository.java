package com.tpe.repository;

import com.tpe.domain.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository  extends MongoRepository<Student,String> {

    Student findByEmail(String email);

    List<Student> findByName(String name);

        @Query("{'lastName':?0}")//ali-ahmad
        List<Student> findStudentByLastNameWithQuery(String lastName);
        //find the student who's phoneNumber is null or empty '' null


       // @Query("{$and:[{'phoneNumber': null},{'address': null}]}")

        // @Query("{'phoneNumber':{$nin:[null, '']}}")//consonant letter
        @Query("{'phoneNumber':{$nin:[null, '']}}")//db.student.find("{'phoneNumber':{$nin:[null, '']}}")
        List<Student> findStudentWithNullAddressandPhoneNumber();

        @Query("{'name': {$regex: '^[aeiou]',$options: 'i'}}")//Ali-aLi-ALI-ALi vowel
        List<Student> findStudentsWhoseNameStartWithVowelLetter();



}
