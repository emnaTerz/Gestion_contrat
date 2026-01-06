package com.emna.micro_service2.Repository;
import com.emna.micro_service2.entities.ContratQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratQRepository extends JpaRepository<ContratQ, String> {
}
