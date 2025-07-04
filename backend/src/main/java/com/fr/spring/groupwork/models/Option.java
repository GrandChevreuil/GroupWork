package com.fr.spring.groupwork.models;

import javax.persistence.*;
import com.fr.spring.groupwork.models.enums.EOption;

/**
 * File Option.java
 * This class represents an option entity in the system.
 * It includes an ID and a name, which is an enumeration of EOption.
 * @author Mathis Mauprivez
 * @date 19/06/2025
 */

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true, nullable = false)
    private EOption name;

    public Option() {
    }

    public Option(EOption name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EOption getName() {
        return name;
    }

    public void setName(EOption name) {
        this.name = name;
    }
}