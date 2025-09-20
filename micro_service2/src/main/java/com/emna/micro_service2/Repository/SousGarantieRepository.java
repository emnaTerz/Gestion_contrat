package com.emna.micro_service2.Repository;



import com.emna.micro_service2.entities.SousGarantie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SousGarantieRepository extends JpaRepository<SousGarantie, Long> {
    List<SousGarantie> findByGarantieId(Long garantieId);  // pour récupérer les sous-garanties d’une garantie
    @Modifying
    @Transactional
    @Query("DELETE FROM SousGarantie s WHERE s.garantie.id = :garantieId")
    void deleteByGarantieId(@Param("garantieId") Long garantieId);
}
