package com.emna.micro_service2.Service;



import com.emna.micro_service2.Repository.ClauseGarantieRepository;
import com.emna.micro_service2.Repository.ClausierRepository;
import com.emna.micro_service2.dto.ClauseGarantieDTO;
import com.emna.micro_service2.entities.ClauseGarantie;
import com.emna.micro_service2.entities.SousGarantie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClauseGarantieService {

    private final ClauseGarantieRepository clauseGarantieRepository;

    public ClauseGarantieService(ClauseGarantieRepository clauseGarantieRepository) {
        this.clauseGarantieRepository = clauseGarantieRepository;
    }

    // Ajouter une clause
    public ClauseGarantie addClause(ClauseGarantie clauseGarantie) {
        return clauseGarantieRepository.save(clauseGarantie);
    }

    // Lister toutes les clauses d'une sous-garantie
    @Transactional(readOnly = true)
    public List<ClauseGarantie> getClausesBySousGarantie(SousGarantie sousGarantie) {
        return clauseGarantieRepository.findBySousGarantie(sousGarantie);
    }



    // Supprimer une clause par son ID
    public void delete(Long id) {
        clauseGarantieRepository.deleteById(id);
    }

    // Supprimer toutes les clauses d'une sous-garantie
    public void deleteClausesBySousGarantie(SousGarantie sousGarantie) {
        clauseGarantieRepository.deleteBySousGarantie(sousGarantie);
    }

    // Optionnel : récupérer une clause par ID
    public Optional<ClauseGarantie> getClauseById(Long id) {
        return clauseGarantieRepository.findById(id);
    }
}
