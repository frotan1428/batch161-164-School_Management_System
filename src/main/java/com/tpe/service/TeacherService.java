package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.domain.Teacher;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Teacher getTeacherById(String id) {
        return teacherRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Teacher is not found with id : " + id));
    }

    public void deleteTeacherById(String id) {
        Teacher teacher = getTeacherById(id);
        teacherRepository.delete(teacher);
    }

    public List<Teacher> findAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        if (teachers.isEmpty()) {
            throw new ResourceNotFoundException("Teacher List is empty : ");
        }
        return teachers;
    }

    public List<Teacher> getTeacherByLastName(String lastName) {
        return teacherRepository.findByLastName(lastName);
    }
}
