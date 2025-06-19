package com.fr.spring.groupwork.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * Entity Classe représentant une classe d'étudiants et son responsable enseignant.
 * Le nom de la classe sera généré selon l'option + suffixe incrémental (_1, _2, ...).
 */
@Entity
@Table(name = "classes")
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    private Option option;

    @ManyToMany
    @JoinTable(
        name = "classe_students",
        joinColumns = @JoinColumn(name = "classe_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<User> students = new HashSet<>();

    public Classe() {}

    public Classe(Option option) {
        this.option = option;
        // Le name sera initialisé dans la couche service ou via un callback @PrePersist
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }
}
