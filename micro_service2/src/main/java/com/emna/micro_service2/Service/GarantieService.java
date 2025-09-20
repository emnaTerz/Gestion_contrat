package com.emna.micro_service2.Service;



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

    public GarantieService(SousGarantieRepository sousGarantieRepository, GarantieRepository garantieRepository) {
        this.sousGarantieRepository = sousGarantieRepository;
        this.garantieRepository = garantieRepository;
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

        sousGarantieRepository.deleteByGarantieId(id);
        garantieRepository.deleteById(id);
    }
}
