package com.emna.micro_service2.Repository;

import com.emna.micro_service2.entities.ClauseGarantie;
import com.emna.micro_service2.entities.SousGarantie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClauseGarantieRepository extends JpaRepository<ClauseGarantie, Long> {

    // Lister toutes les clauses pour une sous-garantie donnée
    List<ClauseGarantie> findBySousGarantie(SousGarantie sousGarantie);

    // Optionnel : supprimer toutes les clauses d'une sous-garantie
    void deleteBySousGarantie(SousGarantie sousGarantie);
}

