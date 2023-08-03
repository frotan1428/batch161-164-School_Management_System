package com.tpe.repository;

import com.tpe.domain.Student;
import com.tpe.domain.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher,String> {
    Teacher findByEmail(String email);
}
