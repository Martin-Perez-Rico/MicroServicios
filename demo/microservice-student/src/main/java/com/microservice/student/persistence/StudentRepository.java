package com.microservice.student.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.microservice.student.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByCourseId(Long courseId);

}
