package com.tpe.repository;

import com.tpe.domain.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository  extends MongoRepository<Student,String> {

    Student findByEmail(String email);

    List<Student> findByName(String name);
}
