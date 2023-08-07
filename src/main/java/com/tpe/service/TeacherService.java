package com.tpe.service;

import com.tpe.domain.Teacher;
import com.tpe.dto.TeacherDto;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher saveTeacher(Teacher teacher) {
        // Check if a teacher with the same email already exists
        Teacher existingTeacher = teacherRepository.findByEmail(teacher.getEmail());
        if (existingTeacher != null) {
            // An existing teacher with the same email already exists
            throw new ConflictException("Teacher with the same email already exists.");
        }

        // If no existing teacher with the same email, save the new teacher to the database
        return teacherRepository.save(teacher);
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

    public void updateTeacherById(String id, TeacherDto teacherDto) {
        // Get the existing teacher from the database by ID
        Teacher existingTeacher = getTeacherById(id);

        // Check if the email being updated already exists for any other teacher
        if (!existingTeacher.getEmail().equals(teacherDto.getEmail())) {
            Teacher teacherWithUpdatedEmail = teacherRepository.findByEmail(teacherDto.getEmail());
            if (teacherWithUpdatedEmail != null) {
                throw new ConflictException("Email already exists for another teacher.");
            }
        }

        // Update the fields of the existing teacher with the new values
        existingTeacher.setName(teacherDto.getName());
        existingTeacher.setLastName(teacherDto.getLastName());
        existingTeacher.setEmail(teacherDto.getEmail());
        existingTeacher.setPhoneNumber(teacherDto.getPhoneNumber());

        // Save the updated teacher to the database
        teacherRepository.save(existingTeacher);
    }

    public List<Teacher> getTeacherByName(String name) {
        List<Teacher> teachers = teacherRepository.findByName(name);
        if (teachers.isEmpty()) {
            throw new ResourceNotFoundException("No teachers found with name: " + name);
        }
        return teachers;
    }

    public Page<Teacher> getAllTeachersWithPage(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    public TeacherDto getTeacherDTOById(String id) {
        return teacherRepository.findTeacherByDto(id).orElseThrow(() ->
                new ResourceNotFoundException("Teacher with ID " + id + " not found"));
    }


}
