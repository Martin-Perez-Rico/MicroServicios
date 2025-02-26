package com.microservice.student;

import java.util.List;

import com.microservice.student.entities.Student;

public class DataProvider {
    
    public static List<Student> getStudents() {
        return List.of(
            new Student(1L, "Juan Perez", "jua.perez@example.com", 1L),
            new Student(2L, "María González", "mar.gon@example.com", 2L),
            new Student(3L, "Carlos López", "car.lop@example.com", 3L),
            new Student(4L, "Ana Martínez", "ana.mar@example.com", 1L),
            new Student(5L, "Pedro Sánchez", "pedro.san@example.com", 3L)
        );
    }

    public static Student getNewStudent() {
        return new Student(null, "John Doe", "jhondoe@gmail.com", 2L);
    }

    public static List<Student> getStudentsByCourse() {
        return List.of(
            new Student(3L, "Carlos López", "car.lop@example.com", 3L),
            new Student(5L, "Pedro Sánchez", "pedro.san@example.com", 3L)
        );
    }
}
