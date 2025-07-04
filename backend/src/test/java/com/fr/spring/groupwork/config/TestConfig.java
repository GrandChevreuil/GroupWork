package com.fr.spring.groupwork.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Configuration pour les tests
 * Cette configuration garantit que les tests utilisent une base de données H2 en mémoire
 * indépendamment de la configuration de l'environnement
 */
@TestConfiguration
public class TestConfig {

    /**
     * Crée une source de données H2 en mémoire pour les tests
     * @return DataSource pour les tests
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }
}
