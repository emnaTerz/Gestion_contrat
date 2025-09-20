package com.emna.micro_service2.Controller;


import com.emna.jwt_service.Service.JwtService;
import com.emna.micro_service2.Service.HistoriqueContratService;
import com.emna.micro_service2.dto.ContratDTO;
import com.emna.micro_service2.dto.Responses.ContratResponseDTO;
import com.emna.micro_service2.entities.Contrat;
import com.emna.micro_service2.Service.ContratService;
import com.emna.micro_service2.entities.HistoriqueContrat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/contrat")
public class ContratController {

    @Autowired
    private ContratService contratService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private HistoriqueContratService historiqueContratService;


        @PostMapping("/creer")
        public ResponseEntity<Contrat> creerContrat(@RequestBody ContratDTO dto, HttpServletRequest request) throws Exception {
            return ResponseEntity.ok(contratService.creerContratComplet(dto, request));
        }

        @PutMapping("/modifier")
        public ResponseEntity<Contrat> modifierContrat(@RequestBody ContratDTO dto, HttpServletRequest request) throws Exception {
            return ResponseEntity.ok(contratService.modifierContrat(dto, request));
        }



    @GetMapping("/{numPolice}")
    public ResponseEntity<?> getContrat(@PathVariable String numPolice, HttpServletRequest request) {
        try {
            ContratResponseDTO contrat = contratService.getContratComplet(numPolice, request);
            return ResponseEntity.ok(contrat);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage()); // 401 si token invalide ou manquant
        }
    }


    @PostMapping("/unlock/{numPolice}")
    public ResponseEntity<?> unlockContrat(
            @PathVariable String numPolice,
            @RequestParam boolean cancelled,
            @RequestParam LocalDateTime startTime, // ✅ envoyé par le front
            HttpServletRequest request
    ) throws Exception {
        contratService.unlockContrat(numPolice, request, cancelled, startTime);
        return ResponseEntity.ok("Contrat déverrouillé");
    }
    @PostMapping("/lock/{numPolice}")
    public ResponseEntity<Void> lockContrat(@PathVariable String numPolice, HttpServletRequest request) throws Exception {
        contratService.lockContrat(numPolice, request);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/exists/{numPolice}")
    public boolean contratExists(@PathVariable String numPolice) {
        return contratService.existsByNumPolice(numPolice);
    }
    @GetMapping("/historique")
    public ResponseEntity<?> getHistorique(
            HttpServletRequest request
    ) throws Exception {
        // -------------------- TOKEN --------------------
        String token = jwtService.getTokenFromRequest(request);
        if (token == null) throw new Exception("Token manquant");

        String username = jwtService.extractUserName(token);
        if (!jwtService.isTokenValid(token, username)) throw new Exception("Token invalide");

        // -------------------- HISTORIQUE --------------------
        List<HistoriqueContrat> historique = historiqueContratService.getAllHistorique();
        return ResponseEntity.ok(historique);
    }
}
