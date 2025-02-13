package com.microservice.course.http.response;

import java.util.List;

import com.microservice.course.dto.StudentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentByCourseResponse {
    
    private String courseName;
    private String teacher;
    private List<StudentDTO> students;
}
