package com.emna.micro_service2.Repository;

import com.emna.micro_service2.entities.ExclusionGlobale;
import com.emna.micro_service2.entities.enums.Branche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExclusionGlobaleRepository extends JpaRepository<ExclusionGlobale, UUID> {
    @Query("""
    SELECT e
    FROM ExclusionGlobale e
    WHERE e.branche = :branche
""")
    List<ExclusionGlobale> findByBranche(@Param("branche") Branche branche);

    void deleteById(UUID id);
}

