package com.microservice.course.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.microservice.course.DataProvider;
import com.microservice.course.client.StudentClient;
import com.microservice.course.entities.Course;
import com.microservice.course.http.response.StudentByCourseResponse;
import com.microservice.course.persistence.ICourseRepository;

public class CourseServiceImplTest {
    
    @Mock
    private ICourseRepository courseRepository;

    @Mock
    private StudentClient studentClient;
    
    @InjectMocks
    private CourseServiceImpl courseService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testFindAll() {
        // Arrange
        when(courseRepository.findAll()).thenReturn(DataProvider.getCourses());
        
        // Act
        List<Course> courses = courseService.findAll();
        
        // Assert
        assertEquals(5, courses.size());
        assertEquals("Matematicas", courses.get(0).getName());
        assertEquals("Biología", courses.get(4).getName());
    }
    
    @Test
    public void testFindById() {
        // Arrange
        Course course = DataProvider.getCourses().get(0);
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        
        // Act
        Course foundCourse = courseService.findById(1L);
        
        // Assert
        assertNotNull(foundCourse);
        assertEquals("Matematicas", foundCourse.getName());
        verify(courseRepository, times(1)).findById(1L);
    }
    
    @Test
    public void testSave() {

        // Arrange
        Course course = DataProvider.getNewCourse();

        // Act
        courseService.save(course);

        // Assert
        ArgumentCaptor<Course> argument = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository, times(1)).save(argument.capture());
        assertEquals("Mecanica", argument.getValue().getName());
    }
    
    @Test
    public void testDelete() {
        // Arrange
        Long id = 1L;

        // Act
        courseService.deleteById(id);

        // Assert
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(courseRepository, times(1)).deleteById(argument.capture());
        assertEquals(1L, argument.getValue());
    }
    
    @Test
    public void testFindStudentsByCourse() {
        // Arrange
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(DataProvider.getCourses().get(0)));
        when(studentClient.findAllStudentByCourse(anyLong())).thenReturn(DataProvider.getStudents().getStudents());
        
        // Act
        StudentByCourseResponse foundStudents = courseService.findStudentsByCourse(1L);
        
        // Assert
        assertNotNull(foundStudents);
        assertEquals("Matematicas", foundStudents.getCourseName());
        assertEquals("Profesor A", foundStudents.getTeacher());
        assertEquals(3, foundStudents.getStudents().size());
        verify(courseRepository, times(1)).findById(1L);
        verify(studentClient, times(1)).findAllStudentByCourse(1L);
    }
}