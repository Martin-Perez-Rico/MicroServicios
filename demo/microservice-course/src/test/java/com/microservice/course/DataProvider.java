package com.microservice.course;

import java.util.List;

import com.microservice.course.dto.StudentDTO;
import com.microservice.course.entities.Course;
import com.microservice.course.http.response.StudentByCourseResponse;

public class DataProvider {
    
    public static List<Course> getCourses() {
        return List.of(
            new Course(1L, "Matematicas", "Profesor A"),
            new Course(2L, "Física", "Profesor B"),
            new Course(3L, "Historia", "Profesor C"),
            new Course(4L, "Literatura", "Profesor D"),
            new Course(5L, "Biología", "Profesor E")
        );
    }

    public static Course getNewCourse() {
        return new Course(null, "Mecanica", "Profesor F");
    }

    public static StudentByCourseResponse getStudents() {
        return new StudentByCourseResponse("Matematicas", "Profesor A", List.of(
            new StudentDTO("Juan", "Perez", 1L),
            new StudentDTO("Maria", "Lopez", 1L),
            new StudentDTO("Pedro", "Gomez", 1L)
        ));
    }
}
