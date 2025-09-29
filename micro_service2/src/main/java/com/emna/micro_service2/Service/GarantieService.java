package com.emna.micro_service2.Service;



import com.emna.micro_service2.Repository.ClausierRepository;
import com.emna.micro_service2.Repository.ExclusionRepository;
import com.emna.micro_service2.Repository.SousGarantieRepository;
import com.emna.micro_service2.entities.Garantie;
import com.emna.micro_service2.Repository.GarantieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GarantieService {
    @Autowired
    private SousGarantieRepository sousGarantieRepository;
    private final GarantieRepository garantieRepository;
    private final ClausierRepository clausierRepository;

    private final ExclusionRepository exclusionRepository;

    public GarantieService( GarantieRepository garantieRepository, ClausierRepository clausierRepository, ExclusionRepository exclusionRepository) {
        this.garantieRepository = garantieRepository;
        this.clausierRepository = clausierRepository;
        this.exclusionRepository = exclusionRepository;
    }

    // Créer ou mettre à jour une garantie
    public Garantie createOrUpdate(Garantie garantie) {
        return garantieRepository.save(garantie);
    }

    // Obtenir toutes les garanties
    public List<Garantie> getAll() {
        return garantieRepository.findAll();
    }

    // Obtenir par ID
    public Optional<Garantie> getById(Long id) {
        return garantieRepository.findById(id);
    }

    // Supprimer par ID
    public void delete(Long id) {
        clausierRepository.deleteByGarantieId(id);;
        exclusionRepository.deleteByGarantieId(id);
        sousGarantieRepository.deleteByGarantieId(id);
        garantieRepository.deleteById(id);
    }
}
