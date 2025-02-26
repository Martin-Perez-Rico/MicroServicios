package com.microservice.course.client;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import com.microservice.course.dto.StudentDTO;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 8080) // (1)
public class StudentClientTest {

    @Autowired
    private StudentClient studentClient; // (2)

    @BeforeEach
    public void setUp() {
        // Configurar WireMock para simular la respuesta del servicio
        stubFor(get(urlPathMatching("/student/api/student/search-by-course/1")) // (3)
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\": 1, \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"courseId\": 1}]")));
    }

    @Test
    public void testFindAllStudentByCourse() {
        // Llamar al método del cliente Feign
        List<StudentDTO> students = studentClient.findAllStudentByCourse(1L); // (4)

        // Verificar que la respuesta no sea nula
        assertNotNull(students); // (5)

        // Verificar que la lista no esté vacía
        assertFalse(students.isEmpty()); // (6)

        // Verificar que los estudiantes pertenezcan al curso con el ID proporcionado
        students.forEach(student -> assertEquals(1L, student.getCourseId())); // (7)
    }
}