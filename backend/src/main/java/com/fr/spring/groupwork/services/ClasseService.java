package com.fr.spring.groupwork.services;

import java.util.List;
import java.util.Optional;
import com.fr.spring.groupwork.models.Classe;

public interface ClasseService {
    Classe createClasse(Long optionId);
    List<Classe> getAllClasses();
    Optional<Classe> getClasseById(Long id);
    void deleteClasse(Long id);
}
