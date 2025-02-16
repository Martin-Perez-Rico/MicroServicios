package com.microservice.course.service;

import java.util.List;

import com.microservice.course.entities.Course;
import com.microservice.course.http.response.StudentByCourseResponse;

public interface ICourseService {

    List<Course> findAll();

    Course findById(Long id);

    void save(Course course);

    void deleteById(Long id);

    StudentByCourseResponse findStudentsByCourse(Long id);
}
