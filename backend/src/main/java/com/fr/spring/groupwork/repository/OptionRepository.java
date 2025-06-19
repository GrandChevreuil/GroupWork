package com.fr.spring.groupwork.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fr.spring.groupwork.models.Option;
import com.fr.spring.groupwork.models.enums.EOption;

@Repository
public interface OptionRepository extends JpaRepository<Option, Integer> {
    Optional<Option> findByName(EOption name);
}
