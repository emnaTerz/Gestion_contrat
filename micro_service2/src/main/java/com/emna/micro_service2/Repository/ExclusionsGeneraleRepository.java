package com.emna.micro_service2.Repository;

import com.emna.micro_service2.entities.ExclusionsGenerale;
import com.emna.micro_service2.entities.enums.Branche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExclusionsGeneraleRepository extends JpaRepository<ExclusionsGenerale, Long> {
    /**
     * Trouve les exclusions générales par branche
     * @param branche la branche recherchée
     * @return Liste des exclusions générales pour cette branche
     */
    List<ExclusionsGenerale> findByBranche(Branche branche);

}
