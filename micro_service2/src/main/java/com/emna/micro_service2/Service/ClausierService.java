package com.emna.micro_service2.Service;



import com.emna.micro_service2.entities.Clausier;
import com.emna.micro_service2.Repository.ClausierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClausierService {

    private final ClausierRepository clausierRepository;

    public ClausierService(ClausierRepository clausierRepository) {
        this.clausierRepository = clausierRepository;
    }

    // Créer ou mettre à jour
    public Clausier createOrUpdate(Clausier clausier) {
        return clausierRepository.save(clausier);
    }

    // Obtenir toutes les clausiers
    public List<Clausier> getAll() {
        return clausierRepository.findAll();
    }

    // Obtenir par ID
    public Optional<Clausier> getById(Long id) {
        return clausierRepository.findById(id);
    }

    // Obtenir par sous-garantie
    public List<Clausier> getBySousGarantie(Long sousGarantieId) {
        return clausierRepository.findBySousGarantieId(sousGarantieId);
    }


    // Supprimer
    public void delete(Long id) {
        clausierRepository.deleteById(id);
    }
}
