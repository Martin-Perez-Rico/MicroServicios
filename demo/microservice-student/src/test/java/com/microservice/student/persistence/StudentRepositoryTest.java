package com.microservice.student.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.microservice.student.DataProvider;
import com.microservice.student.entities.Student;

@DataJpaTest
@ActiveProfiles("test")
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {
        // Datos de prueba
        
    }

    @Test
    public void testFindAll() {
        // Act
        List<Student> students = studentRepository.findAll();

        // Assert
        assertEquals(5, students.size());
        assertEquals("Juan Perez", students.get(0).getName());
        assertEquals("Pedro Sánchez", students.get(4).getName());
    }

    @Test
    public void testFindById() {
        // Arrange
        Student student = studentRepository.findAll().get(0);
        Long id = student.getId();

        // Act
        Optional<Student> foundStudent = studentRepository.findById(id);

        // Assert
        assertTrue(foundStudent.isPresent());
        assertEquals("Juan Perez", foundStudent.get().getName());
    }

    @Test
    public void testFindByCourseId() {
        // Act
        List<Student> students = studentRepository.findByCourseId(1L);

        // Assert
        assertEquals(2, students.size());
        assertEquals("Juan Perez", students.get(0).getName());
        assertEquals("Ana Martínez", students.get(1).getName());
    }

    @Test
    public void testSave() {
        // Arrange
        Student newStudent = DataProvider.getNewStudent();
        
        // Act
        Student savedStudent = studentRepository.save(newStudent);

        // Assert
        assertNotNull(savedStudent.getId());
        assertEquals("John Doe", savedStudent.getName());
    }

    @Test
    public void testDeleteById() {
        // Arrange
        Student student = studentRepository.findAll().get(0);
        Long id = student.getId();

        // Act
        studentRepository.deleteById(id);

        // Assert
        assertFalse(studentRepository.findById(id).isPresent());
    }
}