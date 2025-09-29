package com.emna.micro_service2.Service;


import com.emna.jwt_service.Service.JwtService;
import com.emna.micro_service2.Repository.TarifRepository;
import com.emna.micro_service2.entities.Tarif;
import com.emna.micro_service2.entities.enums.Branche;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifService {

    private final TarifRepository tarifRepository;
    private final JwtService jwtService; // service pour le token

    public TarifService(TarifRepository tarifRepository, JwtService jwtService) {
        this.tarifRepository = tarifRepository;
        this.jwtService = jwtService;
    }

    private void validateToken(HttpServletRequest request) throws Exception {
        String token = jwtService.getTokenFromRequest(request);
        if (token == null) throw new Exception("Token manquant");
        String username = jwtService.extractUserName(token);
        if (!jwtService.isTokenValid(token, username)) throw new Exception("Token invalide");
    }

    public Tarif createTarif(Tarif tarif, HttpServletRequest request) throws Exception {
        validateToken(request);
        return tarifRepository.save(tarif);
    }

    public Tarif updateTarif(Long id, Tarif updatedTarif, HttpServletRequest request) throws Exception {
        validateToken(request);
        Tarif existing = tarifRepository.findById(id)
                .orElseThrow(() -> new Exception("Tarif introuvable"));
        existing.setBranche(updatedTarif.getBranche());
        existing.setFq(updatedTarif.getFq());
        existing.setFeFg(updatedTarif.getFeFg());
        existing.setPrixAdhesion(updatedTarif.getPrixAdhesion());
        existing.setTaux(updatedTarif.getTaux());
        return tarifRepository.save(existing);
    }

    public List<Tarif> getAllTarifs(HttpServletRequest request) throws Exception {
        validateToken(request);
        return tarifRepository.findAll();
    }

    public Tarif getTarifByBranche(Branche branche) throws Exception {
        return tarifRepository.findByBranche(branche)
                .orElseThrow(() -> new Exception("Tarif pour la branche " + branche + " introuvable"));
    }

}
