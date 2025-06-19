package com.fr.spring.groupwork.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fr.spring.groupwork.models.Classe;
import com.fr.spring.groupwork.models.Option;
import com.fr.spring.groupwork.models.User;
import com.fr.spring.groupwork.repository.ClasseRepository;
import com.fr.spring.groupwork.repository.OptionRepository;
import com.fr.spring.groupwork.repository.UserRepository;

/**
 * Implémentation de ClasseService pour la gestion des classes.
 */
@Service
public class ClasseServiceImpl implements ClasseService {

    private final ClasseRepository classeRepository;
    private final OptionRepository optionRepository;
    private final UserRepository userRepository;

    @Autowired
    public ClasseServiceImpl(ClasseRepository classeRepository,
                             OptionRepository optionRepository,
                             UserRepository userRepository) {
        this.classeRepository = classeRepository;
        this.optionRepository = optionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Classe createClasse(Long optionId) {
        Integer optId = optionId.intValue();
        Option option = optionRepository.findById(optId)
            .orElseThrow(() -> new RuntimeException("Option not found"));

        // Génération du nom unique: OPTION_NAME_1, _2, ...
        List<Classe> existing = classeRepository.findByOption(option);
        int index = existing.size() + 1;
        String base = option.getName().name();
        // Collecte des noms existants pour accélérer la recherche
        java.util.Set<String> existingNames = existing.stream()
            .map(Classe::getName)
            .collect(java.util.stream.Collectors.toSet());
        String name = base + "_" + index;
        while (existingNames.contains(name)) {
            index++;
            name = base + "_" + index;
        }

        Classe classe = new Classe(option);
        classe.setName(name);
        return classeRepository.save(classe);
    }

    @Override
    public List<Classe> getAllClasses() {
        return classeRepository.findAll();
    }

    @Override
    public Optional<Classe> getClasseById(Long id) {
        return classeRepository.findById(id);
    }

    @Override
    public void deleteClasse(Long id) {
        classeRepository.deleteById(id);
    }
}
