package com.emna.micro_service2.Repository;

import com.emna.micro_service2.entities.Extension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtensionRepository extends JpaRepository<Extension, Long> {
    List<Extension> findByNumPolice(String numPolice);}
