package com.emna.micro_service2.Repository;



import com.emna.micro_service2.entities.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, String> {
    boolean existsByNumPolice(String numPolice);
   /* Optional<Contrat> findByEditingUser(String username);*/
    List<Contrat> findByEditingUser(String username);


}

