package com.emna.micro_service2.Repository;


import com.emna.micro_service2.entities.HistoriqueContrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriqueContratRepository extends JpaRepository<HistoriqueContrat, Long> {


}

