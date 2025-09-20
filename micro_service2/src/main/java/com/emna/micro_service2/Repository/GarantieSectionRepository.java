package com.emna.micro_service2.Repository;



import com.emna.micro_service2.entities.GarantieSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GarantieSectionRepository extends JpaRepository<GarantieSection, Long> {
    List<GarantieSection> findBySectionId(Long sectionId);  // pour récupérer les garanties d’une section
    List<GarantieSection> findBySection_Id(Long sectionId);

}

