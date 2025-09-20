package com.emna.micro_service2.Repository;

import com.emna.micro_service2.entities.Adherent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository pour l'entité Adherent
@Repository
public interface AdherentRepository extends JpaRepository<Adherent, String> {
    // Ici, String est le type du codeId de l'adhérent
}
