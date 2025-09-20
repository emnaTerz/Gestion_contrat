package com.emna.micro_service2.Repository;


import com.emna.micro_service2.entities.Clausier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ClausierRepository extends JpaRepository<Clausier, Long> {

    // récupérer toutes les clauses d’une sous-garantie
    List<Clausier> findBySousGarantieId(Long sousGarantieId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Clausier c WHERE c.sousGarantie.id = :sousGarantieId")
    void deleteBySousGarantieId(@Param("sousGarantieId") Long sousGarantieId);
}
