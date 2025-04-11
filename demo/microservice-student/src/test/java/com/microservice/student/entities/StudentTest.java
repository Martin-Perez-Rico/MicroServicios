package com.microservice.student.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.microservice.student.DataProvider;

@DataJpaTest
@ActiveProfiles("test")
public class StudentTest {

    @Autowired
    private TestEntityManager entityManager; // (1)

    @Test
    public void testStudentEntityMapping() {
        // Crear una instancia de Student
        Student student = DataProvider.getNewStudent(); // (2)

        // Persistir la entidad en la base de datos
        Student savedStudent = entityManager.persistAndFlush(student); // (3)

        // Verificar que la entidad se ha guardado correctamente
        assertNotNull(savedStudent.getId()); // (4)
        assertEquals("John Doe", savedStudent.getName()); // (5)
        assertEquals("jhondoe@gmail.com", savedStudent.getEmail()); // (6)
        assertEquals(2L, savedStudent.getCourseId()); // (7)
    }
}
