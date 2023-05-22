package com.dojoapp.Dojo.System.Application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        var student = new Student("John", "Doe", Rank.WHITE);
        studentRepository.save(student);
    }

    @Test
    void findByRank() {
        var expectedStudent = new Student("John", "Doe", Rank.WHITE);
        var expectedFirstName = expectedStudent.getFirstName();
        var expectedLastName = expectedStudent.getLastName();
        var expectedRank = expectedStudent.getRank();

        var query = studentRepository.findByRank(Rank.WHITE);
        var actualStudent = query.get(0);

        assertAll(() -> {
            assertEquals(expectedFirstName, actualStudent.getFirstName());
            assertEquals(expectedLastName, actualStudent.getLastName());
            assertEquals(expectedRank, actualStudent.getRank());
        });
    }

    @Test
    void findByLastName() {
        var expectedStudent = new Student("John", "Doe", Rank.WHITE);
        var expectedFirstName = expectedStudent.getFirstName();
        var expectedLastName = expectedStudent.getLastName();
        var expectedRank = expectedStudent.getRank();

        var query = studentRepository.findByLastName("Doe");
        var actualStudent = query.get(0);

        assertAll(() -> {
            assertEquals(expectedFirstName, actualStudent.getFirstName());
            assertEquals(expectedLastName, actualStudent.getLastName());
            assertEquals(expectedRank, actualStudent.getRank());
        });
    }

    @Test
    void findByFirstName() {
        var expectedStudent = new Student("John", "Doe", Rank.WHITE);
        var expectedFirstName = expectedStudent.getFirstName();
        var expectedLastName = expectedStudent.getLastName();
        var expectedRank = expectedStudent.getRank();

        var query = studentRepository.findByFirstName("John");
        var actualStudent = query.get(0);

        assertAll(() -> {
            assertEquals(expectedFirstName, actualStudent.getFirstName());
            assertEquals(expectedLastName, actualStudent.getLastName());
            assertEquals(expectedRank, actualStudent.getRank());
        });
    }

}