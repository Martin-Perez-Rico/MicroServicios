package com.microservice.student.service;

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

import com.microservice.student.DataProvider;
import com.microservice.student.entities.Student;
import com.microservice.student.persistence.StudentRepository;

public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        // Arrange

        when(studentRepository.findAll()).thenReturn(DataProvider.getStudents());

        // Act
        List<Student> result = studentService.findAll();

        // Assert
        assertEquals(5, result.size());
        assertEquals("Juan Perez", result.get(0).getName());
        assertEquals("Pedro Sánchez", result.get(4).getName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        // Arranges
        when(studentRepository.findById(1L)).thenReturn(Optional.of(DataProvider.getStudents().get(0)));

        // Act
        Student result = studentService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Juan Perez", result.getName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        // Configurar el comportamiento del mock
        when(studentRepository.findById(1L)).thenReturn(Optional.empty()); // (1)

        // Verificar que se lanza la excepción
        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.findById(1L); // (2)
        });

        // Verificar el mensaje de la excepción
        assertEquals("Student not found", exception.getMessage()); // (3)
    }

    @Test
    public void testSave() {
        // Arrange
        Student student = DataProvider.getNewStudent();

        // Act
        studentService.save(student);

        // Assert
        ArgumentCaptor<Student> argument = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository, times(1)).save(argument.capture());
        assertEquals("John Doe", argument.getValue().getName());
    }

    @Test
    public void testDeleteById() {
        // Arrange
        Long id = 1L;

        // Act
        studentService.deleteById(id);

        // Assert
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(studentRepository, times(1)).deleteById(argument.capture());
        assertEquals(1L, argument.getValue());
    }

    @Test
    public void testFindByCourseId() {
        // Arrange

        when(studentRepository.findByCourseId(anyLong())).thenReturn(DataProvider.getStudentsByCourse());

        // Act
        List<Student> result = studentService.findByCourseId(3L);

        // Assert
        assertEquals(2, result.size());
        verify(studentRepository, times(1)).findByCourseId(anyLong());
        assertEquals("Carlos López", result.get(0).getName());
        assertEquals("Pedro Sánchez", result.get(1).getName());
    }
    
}