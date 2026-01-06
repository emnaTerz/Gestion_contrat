package com.emna.micro_service2.Repository;

import com.emna.micro_service2.entities.ExclusionGlobale;
import com.emna.micro_service2.entities.enums.Branche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExclusionGlobaleRepository extends JpaRepository<ExclusionGlobale, UUID> {

    List<ExclusionGlobale> findByBranche(Branche branche);

    void deleteById(UUID id);
}

