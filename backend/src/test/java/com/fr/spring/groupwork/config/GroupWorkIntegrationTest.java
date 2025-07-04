package com.fr.spring.groupwork.config;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

/**
 * Annotation combinée pour les tests d'intégration
 * Cette annotation applique automatiquement :
 * - SpringBootTest pour le chargement du contexte complet
 * - AutoConfigureMockMvc pour les tests de contrôleur
 * - ActiveProfiles("test") pour utiliser la configuration de test
 * - Import(TestConfig.class) pour importer la configuration spécifique aux tests
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfig.class)
public @interface GroupWorkIntegrationTest {
}
