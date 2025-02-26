package com.microservice.course.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.microservice.course.DataProvider;
import com.microservice.course.service.ICourseService;

import org.springframework.http.MediaType;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    ICourseService courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() throws Exception {

        mockMvc.perform(get("/api/course/all")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers
                .content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void testFindById() throws Exception{

        when(courseService.findById(1L)).thenReturn(DataProvider.getCourses().get(0)); 

        mockMvc.perform(get("/api/course/search/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers
                .content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    void testSave() throws Exception {

        mockMvc.perform(post("/api/course/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Java\",\"teacher\":\"Profesor F\"}"))
                .andExpect(status().isCreated());

    }

    @Test
    void testDeleteById() throws Exception {

        mockMvc.perform(delete("/api/course/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void testFindStudentsByCourse() throws Exception {

        when(courseService.findStudentsByCourse(1L)).thenReturn(DataProvider.getStudents()); 

        mockMvc.perform(get("/api/course/search-students/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers
                .content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }
}
