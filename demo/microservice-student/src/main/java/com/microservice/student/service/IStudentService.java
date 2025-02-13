package com.microservice.student.service;

import java.util.List;

import com.microservice.student.entities.Student;

public interface IStudentService {
    List<Student> findAll();
    Student findById(Long id);
    void save(Student student);
    void deleteById(Long id);
    List<Student> findByCourseId(Long courseId);
}