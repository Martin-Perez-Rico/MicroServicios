package com.microservice.course.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.microservice.course.DataProvider;

@DataJpaTest
@ActiveProfiles("test")
public class CourseTest {
    
    @Autowired
    private TestEntityManager entityManager; // (1)

    @Test
    public void testCourseEntityMapping() {
        // Crear una instancia de Course
        Course course = DataProvider.getNewCourse(); // (2)

        // Persistir la entidad en la base de datos
        Course savedCourse = entityManager.persistAndFlush(course); // (3)

        // Verificar que la entidad se ha guardado correctamente
        assertNotNull(savedCourse.getId()); // (4)
        assertEquals("Mecanica", savedCourse.getName()); // (5)
        assertEquals("Profesor F", savedCourse.getTeacher()); // (6)
    }
}
