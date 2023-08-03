package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.domain.Teacher;
import com.tpe.exception.ConflictException;
import com.tpe.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public void saveTeacher(Teacher teacher) {

        Teacher existTeacher =  teacherRepository.findByEmail(teacher.getEmail());

        if (existTeacher!=null){
            throw new ConflictException("Teacher with this email is already exist ..");
        }
        teacherRepository.save(teacher);
       // teacherRepository.save(teacher);
    }
}
