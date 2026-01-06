package com.emna.micro_service2.Service;


import com.emna.micro_service2.Repository.ExclusionGlobaleRepository;
import com.emna.micro_service2.entities.ExclusionGlobale;
import com.emna.micro_service2.entities.enums.Branche;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExclusionGlobaleService {

    @Autowired
    private ExclusionGlobaleRepository repository;

    // Ajouter une exclusion
    public ExclusionGlobale addExclusion(ExclusionGlobale exclusion) {
        return repository.save(exclusion);
    }

    // Récupérer les exclusions par branche
    public List<ExclusionGlobale> getExclusionsByBranche(Branche branche) {
        return repository.findByBranche(branche);
    }

    // Supprimer une exclusion par ID
    public void deleteExclusion(UUID id) {
        repository.deleteById(id);
    }
}
