package com.emna.micro_service2.Service;

import com.emna.micro_service2.Repository.ExclusionsGeneraleRepository;
import com.emna.micro_service2.entities.ExclusionsGenerale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExclusionsGeneraleService {

    // Simulation d'une base de données en mémoire
    @Autowired
    private ExclusionsGeneraleRepository exclusionsRepository;

    // Création d'une ExclusionsGenerale
    public ExclusionsGenerale creerExclusionsGenerale(ExclusionsGenerale exclusions) {
        return exclusionsRepository.save(exclusions);
    }

    public ExclusionsGenerale ajouterExclusions(Long id, List<String> nouvellesExclusions) throws Exception {
        ExclusionsGenerale eg = exclusionsRepository.findById(id)
                .orElseThrow(() -> new Exception("ExclusionsGenerale introuvable avec id : " + id));
        eg.ajouterExclusions(nouvellesExclusions);
        return exclusionsRepository.save(eg);
    }
}