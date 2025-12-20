package com.emna.micro_service2.Controller;


import com.emna.jwt_service.Service.JwtService;
import com.emna.micro_service2.Service.HistoriqueContratService;
import com.emna.micro_service2.dto.ContratDTO;
import com.emna.micro_service2.dto.HistoriqueContratDTO;
import com.emna.micro_service2.dto.Responses.ContratResponseDTO;
import com.emna.micro_service2.entities.Contrat;
import com.emna.micro_service2.Service.ContratService;
import com.emna.micro_service2.entities.HistoriqueContrat;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;


import java.time.Duration;
import java.time.LocalDateTime;

import java.time.ZoneId;
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
    @Autowired
    private UserDetailsService userDetailsService;




        @PostMapping("/creer")
        public ResponseEntity<Contrat> creerContrat(@RequestBody ContratDTO dto, HttpServletRequest request) throws Exception {
            return ResponseEntity.ok(contratService.creerContratComplet(dto, request));
        }

       @PutMapping("/modifier")
        public ResponseEntity<Contrat> modifierContrat(@RequestBody ContratDTO dto, HttpServletRequest request) throws Exception {
            return ResponseEntity.ok(contratService.modifierContrat(dto, request));
        }

    @GetMapping("/all")
    public List<Contrat> getAllContrats(HttpServletRequest request) throws Exception {
        return contratService.getAllContrats(request);
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
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            HttpServletRequest request
    ) throws Exception {
        System.out.println("numPolice=" + numPolice + ", cancelled=" + cancelled + ", startTime=" + startTime);

        contratService.unlockContrat(numPolice, request, cancelled, startTime);
        return ResponseEntity.ok("Contrat déverrouillé");
    }
    @PostMapping("/lock/{id}")
    public ResponseEntity<?> lockContrat(@PathVariable String id, HttpServletRequest request) throws Exception {
        Contrat contrat = contratService.lockContrat(id, request);
        return ResponseEntity.ok(contrat);
    }

    @GetMapping("/{numPolice}/lock-status")
    public ResponseEntity<Boolean> checkLockStatus(@PathVariable String numPolice) {
        try {
            boolean isUnlocked = contratService.isUnlocked(numPolice);
            return ResponseEntity.ok(!isUnlocked); // Retourne true si verrouillé, false si déverrouillé
        } catch (Exception e) {
            // En cas d'erreur, on considère que le contrat est verrouillé par sécurité
            return ResponseEntity.ok(true);
        }
    }


    @GetMapping("/exists/{numPolice}")
    public boolean contratExists(@PathVariable String numPolice) {
        return contratService.existsByNumPolice(numPolice);
    }
    @GetMapping("/historique")
    public ResponseEntity<?> getHistorique(HttpServletRequest request) throws Exception {
        // -------------------- TOKEN --------------------
        String token = jwtService.getTokenFromRequest(request);
        if (token == null) {
            throw new Exception("Token manquant");
        }

        String username = jwtService.extractUserName(token);

        // Charger l'utilisateur depuis UserDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Valider le token avec UserDetails
        if (!jwtService.isTokenValid(token, userDetails)) {
            throw new Exception("Token invalide");
        }

        // -------------------- HISTORIQUE --------------------
        List<HistoriqueContrat> historique = historiqueContratService.getAllHistorique();
        return ResponseEntity.ok(historique);
    }


    @DeleteMapping("/historique/{id}")
    public ResponseEntity<Void> deleteHistorique(@PathVariable Long id) {
        try {
            historiqueContratService.deleteHistoriqueById(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/enregistrer")
    public ResponseEntity<Void> enregistrerHistorique(
            @RequestBody HistoriqueContratDTO dto,
            HttpServletRequest request
    ) {

        // 1️⃣ Extraire le username depuis le token
        String token = jwtService.getTokenFromRequest(request);
        String username = jwtService.extractUserName(token);

        // 2️⃣ Calculer le temps de réalisation
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Africa/Tunis"));
        long tempsRealisation = Duration
                .between(dto.getStartDate(), now)
                .toMillis();

        // 3️⃣ Appeler le service EXISTANT (inchangé)
        historiqueContratService.enregistrerHistorique(
                dto.getAction(),
                username,
                tempsRealisation
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{numPolice}/status")
    public String getContratStatus(@PathVariable String numPolice) {
        return contratService.getContratStatus(numPolice);
    }
    @PatchMapping("/{numPolice}/status")
    public ResponseEntity<Contrat> toggleStatus(@PathVariable String numPolice) {
        Contrat updatedContrat = contratService.toggleStatus(numPolice);
        if (updatedContrat != null) {
            return ResponseEntity.ok(updatedContrat);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/locked")
    public ResponseEntity<List<Contrat>> getLockedContrats(HttpServletRequest request) {
        try {
            // 1️⃣ Extraire le token depuis l'en-tête Authorization
            String token = jwtService.getTokenFromRequest(request);
            if (token == null) {
                System.out.println("Token manquant dans la requête");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            // 2️⃣ Extraire le username depuis le token
            String username = null;
            try {
                username = jwtService.extractUserName(token);
                System.out.println("USERNAME TOKEN = " + username);
            } catch (Exception ex) {
                System.err.println("Erreur lors de l'extraction du username depuis le token :");
                ex.printStackTrace();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(null);
            }

            if (username == null || username.isEmpty()) {
                System.out.println("Username vide après extraction du token");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            // 3️⃣ Charger UserDetails
            UserDetails userDetails = null;
            try {
                userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("USER DETAILS = " + userDetails.getUsername());
            } catch (Exception ex) {
                System.err.println("Erreur lors du chargement de UserDetails pour : " + username);
                ex.printStackTrace();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            // 4️⃣ Vérifier que le token est valide
            try {
                if (!jwtService.isTokenValid(token, userDetails)) {
                    System.out.println("Token invalide pour user : " + username);
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
                }
            } catch (Exception ex) {
                System.err.println("Erreur lors de la validation du token pour : " + username);
                ex.printStackTrace();
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            // 5️⃣ Récupérer tous les contrats verrouillés
            List<Contrat> lockedContrats = null;
            try {
                lockedContrats = contratService.getLockedContrats();
                System.out.println("Nombre de contrats verrouillés récupérés : " + lockedContrats.size());
            } catch (Exception ex) {
                System.err.println("Erreur lors de la récupération des contrats verrouillés :");
                ex.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }

            // 6️⃣ Retourner la liste
            return ResponseEntity.ok(lockedContrats);

        } catch (Exception e) {
            System.err.println("ERREUR INATTENDUE DANS /locked :");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }}

