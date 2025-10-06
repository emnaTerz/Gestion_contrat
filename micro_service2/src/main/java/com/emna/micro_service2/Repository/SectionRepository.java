package com.emna.micro_service2.Repository;



import com.emna.micro_service2.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByContrat_NumPolice(String numPolice);
    List<Section> findByRcExploitation_Id(Long rcExploitationId);

}
