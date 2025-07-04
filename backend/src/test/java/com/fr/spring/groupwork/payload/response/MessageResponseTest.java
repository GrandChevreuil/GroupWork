package com.fr.spring.groupwork.payload.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * File : MessageResponseTest.java
 * Tests unitaires pour la classe MessageResponse
 * @author Mathis Mauprivez
 * @date 04/07/2025
 */

class MessageResponseTest {

    /**
     * Test du constructeur et du getter
     */
    @Test
    void testConstructorAndGetter() {
        
        String expectedMessage = "Test message";
        
        MessageResponse response = new MessageResponse(expectedMessage);
        
        assertThat(response.getMessage()).isEqualTo(expectedMessage);
    }
    
    /**
     * Test du setter setMessage
     */
    @Test
    void testSetMessage() {
        
        MessageResponse response = new MessageResponse("Initial message");
        String newMessage = "Updated message";
        
        response.setMessage(newMessage);
        
        assertThat(response.getMessage()).isEqualTo(newMessage);
        assertThat(response.getMessage()).isNotEqualTo("Initial message");
    }
}
