package com.emna.micro_service2.Repository;


import com.emna.micro_service2.entities.Exclusion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ExclusionRepository extends JpaRepository<Exclusion, Long> {
    List<Exclusion> findByGarantieId(Long garantieId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Exclusion e WHERE e.garantie.id = :sousGarantieId")
    void deleteBySousGarantieId(@Param("sousGarantieId") Long sousGarantieId);
}
