package com.emna.micro_service2.Service;

import com.emna.micro_service2.entities.HistoriqueContrat;
import com.emna.micro_service2.Repository.HistoriqueContratRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoriqueContratService {

    @Autowired
    private HistoriqueContratRepository historiqueContratRepository;

    /**
     * Crée un enregistrement dans l'historique d'un contrat
     * @param action "Création" ou "Modification"
     * @param username utilisateur ayant fait l'action
     * @param tempsRealisation durée de l’action en millisecondes
     */
    public void enregistrerHistorique(String action, String username, Long tempsRealisation) {
        HistoriqueContrat historique = new HistoriqueContrat();
        historique.setAction(action);
        historique.setUsername(username);
        historique.setDate(LocalDateTime.now());
        historique.setTempsRealisation(tempsRealisation);

        historiqueContratRepository.save(historique);
    }

    public List<HistoriqueContrat> getAllHistorique() {
        return historiqueContratRepository.findAll();
    }

    @Transactional
    public void deleteHistoriqueById(Long id) {
        HistoriqueContrat historique = historiqueContratRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Historique introuvable avec id " + id));
        historiqueContratRepository.delete(historique);
    }
}
