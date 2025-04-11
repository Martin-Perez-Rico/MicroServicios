package com.microservice.student.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.microservice.student.DataProvider;
import com.microservice.student.service.IStudentService;

import org.springframework.http.MediaType;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    IStudentService studentService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() throws Exception {

        mockMvc.perform(get("/api/student/all")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers
                .content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void testFindByCourseId() throws Exception {

        mockMvc.perform(get("/api/student/search-by-course/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers
                .content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    void testFindById() throws Exception{

        when(studentService.findById(1L)).thenReturn(DataProvider.getStudents().get(0)); 

        mockMvc.perform(get("/api/student/search/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers
                .content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void testSave() throws Exception {

        mockMvc.perform(post("/api/student/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"email\":\"jhondoe@gmail.com\",\"courseId\":2}"))
                .andExpect(status().isCreated());

    }
}
 