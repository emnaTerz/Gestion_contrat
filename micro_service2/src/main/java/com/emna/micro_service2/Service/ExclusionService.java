package com.emna.micro_service2.Service;



import com.emna.micro_service2.entities.Exclusion;
import com.emna.micro_service2.Repository.ExclusionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExclusionService {

    private final ExclusionRepository exclusionRepository;

    public ExclusionService(ExclusionRepository exclusionRepository) {
        this.exclusionRepository = exclusionRepository;
    }

    // Créer ou mettre à jour une exclusion
    public Exclusion createOrUpdate(Exclusion exclusion) {
        return exclusionRepository.save(exclusion);
    }

    // Obtenir toutes les exclusions
    public List<Exclusion> getAll() {
        return exclusionRepository.findAll();
    }

    // Obtenir par ID
    public Optional<Exclusion> getById(Long id) {
        return exclusionRepository.findById(id);
    }

    // Supprimer par ID
    public void delete(Long id) {
        exclusionRepository.deleteById(id);
    }
    public List<Exclusion> getByGarantie(Long garantieId) {
        return exclusionRepository.findByGarantieId(garantieId);
    }

}

