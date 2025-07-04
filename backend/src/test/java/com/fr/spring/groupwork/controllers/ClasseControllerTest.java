package com.fr.spring.groupwork.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fr.spring.groupwork.models.Classe;
import com.fr.spring.groupwork.services.ClasseService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * File ClasseControllerTest.java
 * Tests unitaires pour le contrôleur ClasseController
 * @author Mathis Mauprivez
 * @date 04/07/2025
 */

@WebMvcTest(ClasseController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClasseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClasseService classeService;

    @Test
    void createClasse_shouldReturnCreatedClasse() throws Exception {
                Long optionId = 1L;
        Classe mockClasse = new Classe();
        mockClasse.setId(1L);
        mockClasse.setName("OPTION_1");
        
        when(classeService.createClasse(optionId)).thenReturn(mockClasse);
        
                mockMvc.perform(post("/api/classes")
                .param("optionId", optionId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("OPTION_1"));
                
        verify(classeService, times(1)).createClasse(optionId);
    }
    
    @Test
    void getAllClasses_shouldReturnAllClasses() throws Exception {
                Classe classe1 = new Classe();
        classe1.setId(1L);
        classe1.setName("OPTION_1");
        
        Classe classe2 = new Classe();
        classe2.setId(2L);
        classe2.setName("OPTION_2");
        
        List<Classe> classes = Arrays.asList(classe1, classe2);
        
        when(classeService.getAllClasses()).thenReturn(classes);
        
                mockMvc.perform(get("/api/classes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("OPTION_1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("OPTION_2"));
                
        verify(classeService, times(1)).getAllClasses();
    }
    
    @Test
    void getClasseById_whenFound_shouldReturnClasse() throws Exception {
                Long classeId = 1L;
        Classe mockClasse = new Classe();
        mockClasse.setId(classeId);
        mockClasse.setName("OPTION_1");
        
        when(classeService.getClasseById(classeId)).thenReturn(Optional.of(mockClasse));
        
                mockMvc.perform(get("/api/classes/{id}", classeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("OPTION_1"));
                
        verify(classeService, times(1)).getClasseById(classeId);
    }
    
    @Test
    void getClasseById_whenNotFound_shouldReturnNotFound() throws Exception {
                Long classeId = 999L;
        
        when(classeService.getClasseById(classeId)).thenReturn(Optional.empty());
        
                mockMvc.perform(get("/api/classes/{id}", classeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
                
        verify(classeService, times(1)).getClasseById(classeId);
    }
    
    @Test
    void deleteClasse_shouldReturnNoContent() throws Exception {
                Long classeId = 1L;
        doNothing().when(classeService).deleteClasse(classeId);
        
                mockMvc.perform(delete("/api/classes/{id}", classeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
                
        verify(classeService, times(1)).deleteClasse(classeId);
    }
}
