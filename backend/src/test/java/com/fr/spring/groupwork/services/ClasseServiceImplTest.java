package com.fr.spring.groupwork.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fr.spring.groupwork.models.Classe;
import com.fr.spring.groupwork.models.Option;
import com.fr.spring.groupwork.models.enums.EOption;
import com.fr.spring.groupwork.repository.ClasseRepository;
import com.fr.spring.groupwork.repository.OptionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * File ClasseServiceImplTest.java
 * Tests unitaires pour la classe ClasseServiceImpl
 * @author Mathis Mauprivez
 * @date 03/07/2025
 */

@ExtendWith(MockitoExtension.class)
class ClasseServiceImplTest {

    @Mock
    private ClasseRepository classeRepository;

    @Mock
    private OptionRepository optionRepository;

    @InjectMocks
    private ClasseServiceImpl classeService;

    private Option option;

    @BeforeEach
    void setUp() {
        option = new Option(EOption.COMPUTER_SCIENCE);
        option.setId(42);
    }

    @Test
    void createClasse_shouldSaveWithIncrementedName() {
        when(optionRepository.findById(42)).thenReturn(Optional.of(option));
        when(classeRepository.findByOption(option)).thenReturn(Collections.emptyList());
        Classe saved = new Classe(option);
        saved.setName("COMPUTER_SCIENCE_1");
        when(classeRepository.save(any(Classe.class))).thenReturn(saved);

        Classe result = classeService.createClasse(42L);

        assertThat(result.getName()).isEqualTo("COMPUTER_SCIENCE_1");
        verify(classeRepository).save(any(Classe.class));
    }

    // Tests de couverture pour la boucle while et les méthodes basiques
    @Test
    void createClasse_shouldSkipExistingNames() {
        when(optionRepository.findById(42)).thenReturn(Optional.of(option));
        Classe c1 = new Classe(option);
        c1.setName("COMPUTER_SCIENCE_1");
        Classe c2 = new Classe(option);
        c2.setName("COMPUTER_SCIENCE_2");
        when(classeRepository.findByOption(option)).thenReturn(Arrays.asList(c1, c2));
        Classe saved = new Classe(option);
        saved.setName("COMPUTER_SCIENCE_3");
        when(classeRepository.save(any(Classe.class))).thenReturn(saved);

        Classe result = classeService.createClasse(42L);

        assertThat(result.getName()).isEqualTo("COMPUTER_SCIENCE_3");
        verify(classeRepository).save(any(Classe.class));
    }

    @Test
    void createClasse_shouldLoopOnCollision() {
        when(optionRepository.findById(42)).thenReturn(Optional.of(option));
        // Un seul élément existant nommé _2 pour forcer la collision initiale
        Classe cExisting = new Classe(option);
        cExisting.setName("COMPUTER_SCIENCE_2");
        when(classeRepository.findByOption(option))
            .thenReturn(Collections.singletonList(cExisting));

        Classe saved = new Classe(option);
        saved.setName("COMPUTER_SCIENCE_3");
        when(classeRepository.save(any(Classe.class))).thenReturn(saved);

        Classe result = classeService.createClasse(42L);

        assertThat(result.getName()).isEqualTo("COMPUTER_SCIENCE_3");
        verify(classeRepository).save(any(Classe.class));
    }

    @Test
    void getAllClasses_shouldReturnAll() {
        Classe c1 = new Classe(option);
        c1.setName("COMPUTER_SCIENCE_1");
        Classe c2 = new Classe(option);
        c2.setName("COMPUTER_SCIENCE_2");
        List<Classe> list = Arrays.asList(c1, c2);
        when(classeRepository.findAll()).thenReturn(list);

        List<Classe> result = classeService.getAllClasses();

        assertThat(result).isEqualTo(list);
        verify(classeRepository).findAll();
    }

    @Test
    void getClasseById_shouldReturnOptional() {
        Classe c = new Classe(option);
        c.setName("COMPUTER_SCIENCE_1");
        when(classeRepository.findById(7L)).thenReturn(Optional.of(c));

        Optional<Classe> result = classeService.getClasseById(7L);

        assertThat(result).isPresent().contains(c);
        verify(classeRepository).findById(7L);
    }

    @Test
    void deleteClasse_shouldInvokeDelete() {
        doNothing().when(classeRepository).deleteById(9L);

        classeService.deleteClasse(9L);

        verify(classeRepository).deleteById(9L);
    }
}
