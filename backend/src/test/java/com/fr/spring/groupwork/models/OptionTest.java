package com.fr.spring.groupwork.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fr.spring.groupwork.models.enums.EOption;

/**
 * File : OptionTest.java
 * Tests unitaires pour la classe Option
 * @author Mathis Mauprivez
 * @date 04/07/2025
 */

class OptionTest {

    private Option option;
    
    @BeforeEach
    void setUp() {
        option = new Option();
    }
    
    @Test
    void defaultConstructor_shouldCreateEmptyOption() {
        
        assertThat(option.getId()).isNull();
        assertThat(option.getName()).isNull();
    }
    
    @Test
    void constructorWithName_shouldSetName() {
        EOption optionName = EOption.COMPUTER_SCIENCE;
        Option optionWithName = new Option(optionName);
        assertThat(optionWithName.getName()).isEqualTo(optionName);
    }
    
    @Test
    void setAndGetId_shouldWorkCorrectly() {
        
        Integer id = 5;
        option.setId(id);
        assertThat(option.getId()).isEqualTo(id);
    }
    
    @Test
    void setAndGetName_shouldWorkCorrectly() {
        
        EOption name = EOption.MATHEMATICS;
        option.setName(name);
        assertThat(option.getName()).isEqualTo(name);
    }
    
    @Test
    void idShouldBeNullByDefault() {
        
        Option newOption = new Option();
        assertThat(newOption.getId()).isNull();
    }
    
    @Test
    void nameShouldBeNullInDefaultConstructor() {
        
        Option newOption = new Option();
        assertThat(newOption.getName()).isNull();
    }
    
    @Test
    void constructorWithName_shouldNotSetId() {
        EOption optionName = EOption.COMPUTER_SCIENCE;
        Option optionWithName = new Option(optionName);
        
        assertThat(optionWithName.getId()).isNull();
    }
}
