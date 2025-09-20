package com.emna.micro_service2.Repository;

import com.emna.micro_service2.entities.Activite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository pour l'entité Activite
@Repository
public interface ActiviteRepository extends JpaRepository<Activite, String> {
    // Ici, String est le type de l'ID de l'activité
}
