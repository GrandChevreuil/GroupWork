package com.fr.spring.groupwork.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fr.spring.groupwork.models.Classe;
import com.fr.spring.groupwork.models.Option;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {
    List<Classe> findByOption(Option option);
}
