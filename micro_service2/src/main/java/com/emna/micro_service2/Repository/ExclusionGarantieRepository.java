package com.emna.micro_service2.Repository;



import com.emna.micro_service2.entities.ExclusionGarantie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExclusionGarantieRepository extends JpaRepository<ExclusionGarantie, Long> {

    // Récupérer toutes les exclusions d'une garantie d'une section
    List<ExclusionGarantie> findByGarantieSectionId(Long garantieSectionId);

    // Récupérer toutes les exclusions d'une section via les garanties
    List<ExclusionGarantie> findByGarantieSection_SectionId(Long sectionId);
    List<ExclusionGarantie> findByGarantieSection_Id(Long garantieSectionId);
}
