package com.emna.micro_service2.Service;



import com.emna.micro_service2.entities.Clausier;
import com.emna.micro_service2.Repository.ClausierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClausierService {

    private final ClausierRepository clausierRepository;
    private final HistoriqueContratService historiqueContratService;

    public ClausierService(ClausierRepository clausierRepository, HistoriqueContratService historiqueContratService) {
        this.clausierRepository = clausierRepository;
        this.historiqueContratService = historiqueContratService;
    }

    // Créer ou mettre à jour
    public Clausier createOrUpdate(Clausier clausier,String username) {

        historiqueContratService.enregistrerHistorique(
                "Création d' un nouveau clausier " + clausier.getNom(),
                username,
                0L
        );

        return clausierRepository.save(clausier);
    }

    // Obtenir toutes les clausiers
    public List<Clausier> getAll(String username) {

        historiqueContratService.enregistrerHistorique(
                "A consulter les clausiers " ,
                username,
                0L
        );


        return clausierRepository.findAll();
    }

    // Obtenir par ID
    public Optional<Clausier> getById(Long id) {
        return clausierRepository.findById(id);
    }

    // Obtenir par sous-garantie



    // Supprimer
    public void delete(Long id) {

        clausierRepository.deleteById(id);
    }
}
