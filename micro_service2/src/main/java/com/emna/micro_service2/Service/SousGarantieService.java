package com.emna.micro_service2.Service;


import com.emna.micro_service2.Repository.ClausierRepository;
import com.emna.micro_service2.Repository.ExclusionRepository;
import com.emna.micro_service2.entities.SousGarantie;
import com.emna.micro_service2.Repository.SousGarantieRepository;
import com.emna.micro_service2.entities.enums.Branche;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SousGarantieService {

    private final SousGarantieRepository sousGarantieRepository;
    private final ClausierRepository clausierRepository;
    private final ExclusionRepository exclusionRepository;

    public SousGarantieService(SousGarantieRepository sousGarantieRepository, ClausierRepository clausierRepository, ExclusionRepository exclusionRepository) {
        this.sousGarantieRepository = sousGarantieRepository;
        this.clausierRepository = clausierRepository;
        this.exclusionRepository = exclusionRepository;
    }

    // Créer ou mettre à jour une sous-garantie
    public SousGarantie createOrUpdate(SousGarantie sousGarantie) {
        return sousGarantieRepository.save(sousGarantie);
    }

    // Obtenir toutes les sous-garanties
    public List<SousGarantie> getAll() {
        return sousGarantieRepository.findAll();
    }

    // Obtenir par ID
    public Optional<SousGarantie> getById(Long id) {
        return sousGarantieRepository.findById(id);
    }

    // Supprimer par ID
    public void delete(Long id) {

        sousGarantieRepository.deleteById(id);
    }
    public List<SousGarantie> getByGarantie(Long garantieId) {
        return sousGarantieRepository.findByGarantieId(garantieId);
    }
    public List<SousGarantie> getByGarantieAndBranche(Branche branche) {
        return sousGarantieRepository.findByBranche(branche);
    }
    public List<SousGarantie> getSousGaranties(Long garantieId, Branche branche) {
        return sousGarantieRepository.findByGarantieIdAndBranche(garantieId, branche);
    }



}
