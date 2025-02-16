package com.microservice.course.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.course.dto.StudentDTO;

@FeignClient(name = "msvc-student", url = "http://localhost:8080")
public interface StudentClient {
    
    @GetMapping("student/api/student/search-by-course/{courseId}")
    List<StudentDTO> findAllStudentByCourse(@PathVariable Long courseId);
}
