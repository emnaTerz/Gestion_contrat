package com.emna.micro_service2.Repository;


import com.emna.micro_service2.entities.Garantie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarantieRepository extends JpaRepository<Garantie, Long> {
}
