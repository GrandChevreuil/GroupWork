package com.fr.spring.groupwork.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * File : ClasseTest.java
 * Tests unitaires pour la classe Classe
 * @author Mathis Mauprivez
 * @date 04/07/2025
 */
class ClasseTest {

    private Classe classe;
    private Option option;
    
    @BeforeEach
    void setUp() {
        option = new Option();
        option.setId(1);
        option.setName(com.fr.spring.groupwork.models.enums.EOption.COMPUTER_SCIENCE);
        
        classe = new Classe();
    }
    
    @Test
    void constructorWithOption_shouldSetOption() {
        // Arrange & Act
        Classe classeWithOption = new Classe(option);
        
        
        assertThat(classeWithOption.getOption()).isEqualTo(option);
    }
    
    @Test
    void setAndGetId_shouldWorkCorrectly() {
        
        Long id = 5L;
        
        
        classe.setId(id);
        
        
        assertThat(classe.getId()).isEqualTo(id);
    }
    
    @Test
    void setAndGetName_shouldWorkCorrectly() {
        
        String name = "CS_1";
        
        
        classe.setName(name);
        
        
        assertThat(classe.getName()).isEqualTo(name);
    }
    
    @Test
    void setAndGetOption_shouldWorkCorrectly() {
        
        // Option already created in setUp()
        
        
        classe.setOption(option);
        
        
        assertThat(classe.getOption()).isEqualTo(option);
        assertThat(classe.getOption().getId()).isEqualTo(1);
        assertThat(classe.getOption().getName()).isEqualTo(com.fr.spring.groupwork.models.enums.EOption.COMPUTER_SCIENCE);
    }
    
    @Test
    void setAndGetStudents_shouldWorkCorrectly() {
        
        User student1 = new User();
        student1.setId(1L);
        student1.setUsername("student1");
        
        User student2 = new User();
        student2.setId(2L);
        student2.setUsername("student2");
        
        Set<User> students = new HashSet<>();
        students.add(student1);
        students.add(student2);
        
        
        classe.setStudents(students);
        
        
        assertThat(classe.getStudents()).isEqualTo(students);
        assertThat(classe.getStudents()).hasSize(2);
        assertThat(classe.getStudents()).contains(student1, student2);
    }
    
    @Test
    void defaultConstructor_shouldCreateEmptyStudentsSet() {
        // Arrange & Act - Using the classe created in setUp()
        
        
        assertThat(classe.getStudents()).isNotNull();
        assertThat(classe.getStudents()).isEmpty();
    }
}
