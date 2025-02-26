package com.microservice.course.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.microservice.course.DataProvider;
import com.microservice.course.entities.Course;

@DataJpaTest
@ActiveProfiles("test")
public class CourseRepositoryTest {
    
    @Autowired
    private ICourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
 
    }

    @Test
    public void testFindAll() {
        // Act
        List<Course> courses = (List<Course>) courseRepository.findAll();

        // Assert
        assertEquals(5, courses.size());
        assertEquals("Matematicas", courses.get(0).getName());
        assertEquals("Biología", courses.get(4).getName());
    }

    @Test
    public void testFindById() {
        // Arrange
        Course course = ((List<Course>) courseRepository.findAll()).get(0);
        Long id = course.getId();

        // Act
        Optional<Course> foundCourse = courseRepository.findById(id);

        // Assert
        assertTrue(foundCourse.isPresent());
        assertEquals("Matematicas", foundCourse.get().getName());
    }

    @Test
    public void testSave() {
        // Arrange
        Course course = DataProvider.getNewCourse();

        // Act
        Course savedCourse = courseRepository.save(course);

        // Assert
        assertNotNull(savedCourse.getId());
        assertEquals("Mecanica", savedCourse.getName());
        assertEquals("Profesor F", savedCourse.getTeacher());
    }

    @Test
    public void testDelete() {
        // Arrange
        Course course = ((List<Course>) courseRepository.findAll()).get(0);
        Long id = course.getId();

        // Act
        courseRepository.deleteById(id);

        // Assert
        assertFalse(courseRepository.findById(id).isPresent());
    }
}
