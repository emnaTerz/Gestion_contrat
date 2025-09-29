package com.emna.micro_service2.Repository;


import com.emna.micro_service2.entities.Tarif;
import com.emna.micro_service2.entities.enums.Branche;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TarifRepository extends JpaRepository<Tarif, Long> {
    Optional<Tarif> findByBranche(Branche branche);
}
