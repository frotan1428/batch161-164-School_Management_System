package com.tpe.repository;

import com.tpe.domain.Teacher;
import com.tpe.dto.TeacherDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {

    List<Teacher> findByLastName(String lastName);

    List<Teacher> findByName(String name); // Note: Change the method name to findByName

    Teacher findByEmail(String email);

    @Query("{'_id': ?0}") // MongoDB query to find a teacher by ID
    Optional<TeacherDto> findTeacherByDto(String id); // Change the ID type to String


}
