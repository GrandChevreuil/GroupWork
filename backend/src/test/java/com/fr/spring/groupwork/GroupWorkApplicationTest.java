package com.fr.spring.groupwork;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * File : GroupWorkApplicationTest.java
 * Tests unitaires pour la classe GroupWorkApplication
 * @author Mathis Mauprivez
 * @date 04/07/2025
 */

@SpringBootTest
class GroupWorkApplicationTest {

    /**
     * Test simple pour vérifier que l'application démarre correctement
     * Ce test couvre la méthode main en chargeant le contexte Spring
     */
    @Autowired
    private ApplicationContext applicationContext;
    
    @Test
    void contextLoads() {
        // Ce test vérifie que le contexte Spring se charge correctement avec une assertion
        assertThat(applicationContext).isNotNull();
    }
    
    /**
     * Test la classe d'application
     * Nous n'appelons pas directement main() car cela tenterait de démarrer l'application
     * sur le port 8080 qui est déjà utilisé pendant les tests
     */
    @Test
    void testApplicationClass() {
        // Vérifier simplement que la classe d'application peut être instanciée
        GroupWorkApplication application = new GroupWorkApplication();
        assertThat(application).isNotNull();
    }

    /**
     * Test explicite de la méthode main
     * Utilisation d'une approche avec Mockito pour éviter le démarrage réel de l'application
     */
    @Test
    void testMainMethod() {
        try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            // Arrange
            String[] args = {"--server.port=0"}; // Port aléatoire pour éviter les conflits

            // Act
            GroupWorkApplication.main(args);

            // Assert
            mocked.verify(() -> SpringApplication.run(GroupWorkApplication.class, args));
        }
    }
}
