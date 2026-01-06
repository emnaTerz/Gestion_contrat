package com.emna.micro_service2.Controller;





import com.emna.jwt_service.Service.JwtService;
import com.emna.micro_service2.Repository.ClausierRepository;
import com.emna.micro_service2.Service.*;
import com.emna.micro_service2.dto.ExclusionRCRequest;
import com.emna.micro_service2.dto.ExclusionsRequestDTO;
import com.emna.micro_service2.entities.*;
import com.emna.micro_service2.entities.enums.Branche;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/contrat/catalogue")
public class CatalogueController {

    private final GarantieService garantieService;
    private final SousGarantieService sousGarantieService;
    private final ExclusionService exclusionService;
    private final ClausierService clausierService;
    private final ExclusionRCService exclusionRCService;

    private final ExclusionsGeneraleService exclusionsService;

    private final ExclusionGlobaleService exclusionGlobaleService;
    private final JwtService jwtService;
    private final HistoriqueContratService historiqueContratService;
    private final ClausierRepository clausierRepository;
    public CatalogueController(GarantieService garantieService,ClausierRepository clausierRepository,ExclusionGlobaleService exclusionGlobaleService,HistoriqueContratService historiqueContratService,JwtService jwtService, SousGarantieService sousGarantieService, ExclusionService exclusionService, ClausierService clausierService, ExclusionRCService exclusionRCService, ExclusionsGeneraleService exclusionsService) {
        this.garantieService = garantieService;
        this.sousGarantieService = sousGarantieService;
        this.exclusionService = exclusionService;
        this.clausierService = clausierService;
        this.exclusionRCService = exclusionRCService;
        this.exclusionsService = exclusionsService;
        this.jwtService = jwtService;
        this.historiqueContratService=historiqueContratService;
        this.clausierRepository=clausierRepository;
        this.exclusionGlobaleService=exclusionGlobaleService;
    }

    // ---------------- Garanties ----------------
    @PostMapping("/garantie")
    public ResponseEntity<Garantie> createGarantie(
            @RequestBody Garantie garantie,
            @RequestHeader("Authorization") String authorizationHeader
    ) {

        // 🔹 Récupération du token depuis le header
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);  // enlever "Bearer "
        }

        // 🔹 Extraction du username à partir du token JWT
        String username = jwtService.extractUserName(token);

        // 🔹 Création ou mise à jour de la garantie
        Garantie savedGarantie = garantieService.createOrUpdate(garantie);

        // 🔹 Enregistrement dans l’historique
        historiqueContratService.enregistrerHistorique(
                "Création garantie Nom = " + savedGarantie.getLibelle(),
                username,
                0L
        );

        return ResponseEntity.ok(savedGarantie);
    }


    @GetMapping("/garantie")
    public ResponseEntity<List<Garantie>> getAllGaranties() {
        return ResponseEntity.ok(garantieService.getAll());
    }

    @GetMapping("/garantie/{id}")
    public ResponseEntity<Garantie> getGarantieById(@PathVariable Long id) {
        return garantieService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/garantie/{id}")
    public ResponseEntity<Void> deleteGarantie(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader
    ) {

        // 🔹 Récupération du token dans le header
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }

        // 🔹 Extraction du username depuis le token
        String username = jwtService.extractUserName(token);

        // 🔹 Récupération de la garantie avant suppression
        Garantie garantie = garantieService.getById(id).orElse(null);

        // 🔹 Suppression
        garantieService.delete(id);

        // 🔹 Enregistrement historique avec nom
        historiqueContratService.enregistrerHistorique(
                "Suppression garantie : " +
                        (garantie != null ? garantie.getLibelle() : "Garantie inconnue") +
                        " (ID = " + id + ")",
                username,
                0L
        );

        return ResponseEntity.noContent().build();
    }



    // ---------------- Sous-Garanties ----------------
    @PostMapping("/sous-garantie")
    public ResponseEntity<SousGarantie> createSousGarantie(
            @RequestBody SousGarantie sousGarantie,
            @RequestHeader("Authorization") String authorizationHeader
    ) {

        // 🔹 Extraire le token du header Authorization
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // Retirer "Bearer "
        }

        // 🔹 Extraire le nom d'utilisateur depuis le token JWT
        String username = jwtService.extractUserName(token);

        // 🔹 Création / Mise à jour de la sous-garantie
        SousGarantie saved = sousGarantieService.createOrUpdate(sousGarantie);

        // 🔹 Historique
        historiqueContratService.enregistrerHistorique(
                "Création garantie nom = " + saved.getNom(),
                username,
                0L   // tempsRealisation
        );

        return ResponseEntity.ok(saved);
    }


    @GetMapping("/sous-garantie")
    public ResponseEntity<List<SousGarantie>> getAllSousGaranties() {
        return ResponseEntity.ok(sousGarantieService.getAll());
    }
    @GetMapping("/sous-garantie/by-and-branche/{branche}")
    public List<SousGarantie> getByGarantieAndBranche(
            @PathVariable("branche") Branche branche) {
        return sousGarantieService.getByGarantieAndBranche(branche);
    }

    @GetMapping("/sous-garantie/{id}")
    public ResponseEntity<SousGarantie> getSousGarantieById(@PathVariable Long id) {
        return sousGarantieService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // **Récupérer toutes les sous-garanties d’une garantie**
    @GetMapping("/sous-garantie/garantie/{garantieId}")
    public ResponseEntity<List<SousGarantie>> getSousGarantiesByGarantie(@PathVariable Long garantieId) {
        return ResponseEntity.ok(sousGarantieService.getByGarantie(garantieId));
    }

    @DeleteMapping("/sous-garantie/{id}")
    public ResponseEntity<Void> deleteSousGarantie(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader
    ) {

        // 🔹 Récupération du token dans le header
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }

        // 🔹 Extraction du username depuis le token
        String username = jwtService.extractUserName(token);

        // 🔹 Récupération de la sous-garantie avant suppression
        SousGarantie sousGarantie = sousGarantieService.getById(id).orElse(null);

        // 🔹 Suppression
        sousGarantieService.delete(id);

        // 🔹 Enregistrement de l’historique avec le nom
        historiqueContratService.enregistrerHistorique(
                "Suppression sous-garantie : " +
                        (sousGarantie != null ? sousGarantie.getNom() : "Sous-garantie inconnue"),
                username,
                0L
        );

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sous-garantie-branche/{garantieId}")
    public ResponseEntity<List<SousGarantie>> getSousGaranties(
            @PathVariable Long garantieId,
            @RequestParam Branche branche) {
        List<SousGarantie> sousGaranties = sousGarantieService.getSousGaranties(garantieId, branche);
        return ResponseEntity.ok(sousGaranties);
    }
    // ---------------- Exclusions ----------------
    @PostMapping("/exclusion")
    public ResponseEntity<Exclusion> createExclusion(
            @RequestBody Exclusion exclusion,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        // 🔹 Récupération du token dans le header
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }

        // 🔹 Extraction du username depuis le token
        String username = jwtService.extractUserName(token);

        // 🔹 Création de l'exclusion
        Exclusion createdExclusion = exclusionService.createOrUpdate(exclusion);

        // 🔹 Enregistrement de l’historique
        historiqueContratService.enregistrerHistorique(
                "Création exclusion : " ,
                username,
                0L  // tempsRealisation
        );

        return ResponseEntity.ok(createdExclusion);
    }


    @GetMapping("/exclusion")
    public ResponseEntity<List<Exclusion>> getAllExclusions() {
        return ResponseEntity.ok(exclusionService.getAll());
    }

    @GetMapping("/exclusion/{id}")
    public ResponseEntity<Exclusion> getExclusionById(@PathVariable Long id) {
        return exclusionService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // GET exclusions par branche ET garantie ID
    @GetMapping("/exclusion/branche/{branche}/garantie/{garantieId}")
    public ResponseEntity<List<Exclusion>> getExclusionsByBrancheAndGarantieId(
            @PathVariable Branche branche,
            @PathVariable Long garantieId) {
        List<Exclusion> exclusions = exclusionService.getExclusionsByBrancheAndGarantieId(branche, garantieId);
        return ResponseEntity.ok(exclusions);
    }
    // **Récupérer toutes les exclusions d’une garantie**
    @GetMapping("/exclusion/garantie/{garantieId}")
    public ResponseEntity<List<Exclusion>> getExclusionsByGarantie(@PathVariable Long garantieId) {
        return ResponseEntity.ok(exclusionService.getByGarantie(garantieId));
    }

    @DeleteMapping("/exclusion/{id}")
    public ResponseEntity<Void> deleteExclusion(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        // 🔹 Récupération du token dans le header
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }

        // 🔹 Extraction du username depuis le token
        String username = jwtService.extractUserName(token);

        // 🔹 Récupération de l'exclusion avant suppression pour obtenir le nom
        Exclusion exclusion = exclusionService.getById(id)
                .orElseThrow(() -> new RuntimeException("Exclusion non trouvée avec id: " + id));

        // 🔹 Suppression de l'exclusion
        exclusionService.delete(id);

        // 🔹 Enregistrement de l’historique
        historiqueContratService.enregistrerHistorique(
                "Suppression exclusion : " ,
                username,
                0L  // tempsRealisation
        );

        return ResponseEntity.noContent().build();
    }


    // ---------------- Clausiers ----------------
    @PostMapping("/clausier")
    public ResponseEntity<Clausier> createClausier(
            @RequestBody Clausier clausier,
            @RequestHeader("Authorization") String authHeader) {

        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Enlever "Bearer "
            username = jwtService.extractUserName(token); // Extraire le username via le service
            System.out.println("Utilisateur connecté : " + username);
        }

        // Tu peux maintenant utiliser username dans le service si besoin
        return ResponseEntity.ok(clausierService.createOrUpdate(clausier, username));
    }


    @GetMapping("/clausier")
    public ResponseEntity<List<Clausier>> getAllClausiers(
            @RequestHeader("Authorization") String authorizationHeader) {

        // Vérifier et extraire le token du header "Bearer <token>"
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // Supprime "Bearer "
        }

        // Extraire le username via ton service JWT
        String username = jwtService.extractUserName(token);

        // Ici, tu peux utiliser username si nécessaire
        System.out.println("Utilisateur connecté : " + username);

        // Retourner la liste des clausiers
        return ResponseEntity.ok(clausierService.getAll(username));
    }

    @GetMapping("/clausier/{id}")
    public ResponseEntity<Clausier> getClausierById(@PathVariable Long id) {
        return clausierService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/clausier/{id}")
    public ResponseEntity<Void> deleteClausier(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        String username = jwtService.extractUserName(token);

        // Récupération du clausier
        Optional<Clausier> clausierOptional = clausierRepository.findById(id);
        if (clausierOptional.isPresent()) {
            Clausier clausier = clausierOptional.get();

            // Enregistrement dans l'historique
            historiqueContratService.enregistrerHistorique(
                    "A supprimer la clausier " + clausier.getNom(),
                    username,
                    0L
            );

            // Suppression
            clausierService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // ---------------- Exclusions RC ----------------
    @PostMapping("/exclusion-rc")
    public ResponseEntity<ExclusionRC> createExclusionRC(@RequestBody ExclusionRCRequest request) {
        ExclusionRC exclusionRC = exclusionRCService.createExclusion(request.getNom());
        return ResponseEntity.ok(exclusionRC);
    }
    @GetMapping("/exclusion-rc/{id}")
    public ResponseEntity<?> getExclusionRCNoms(@PathVariable Long id) {
        Optional<ExclusionRC> rc = exclusionRCService.getExclusions(id);

        if (rc.isPresent()) {
            String nom = rc.get().getNom(); // Supposant que getNom() existe
            return ResponseEntity.ok(nom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/exclusion-rc")
    public ResponseEntity<List<ExclusionRC>> getAllExclusionsRC() {
        return ResponseEntity.ok(exclusionRCService.getAllExclusions());
    }
    @PostMapping("/exclusion-gen")
    public ResponseEntity<ExclusionsGenerale> createExclusions(@RequestBody ExclusionsGenerale exclusions) {
        ExclusionsGenerale created = exclusionsService.creerExclusionsGenerale(exclusions);
        if (created != null) {
            return ResponseEntity.ok(created);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/exclusion-gen/{id}")
    public ResponseEntity<ExclusionsGenerale> ajouterExclusions(
            @PathVariable Long id,
            @RequestBody ExclusionsRequestDTO request) {
        try {
            ExclusionsGenerale updated = exclusionsService.ajouterExclusions(id, request.getListeExclusion());
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    // ---------------- Exclusions Globale ----------------
    @PostMapping("/exclusion-globale")
    public ResponseEntity<ExclusionGlobale> createExclusionGlobale(
            @RequestBody ExclusionGlobale exclusionGlobale,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        String username = jwtService.extractUserName(token);

        ExclusionGlobale createdExclusion = exclusionGlobaleService.addExclusion(exclusionGlobale);

        historiqueContratService.enregistrerHistorique(
                "Création exclusion globale : ",
                username,
                0L
        );

        return ResponseEntity.ok(createdExclusion);
    }

    @GetMapping("/exclusion-globale/branche/{branche}")
    public ResponseEntity<List<ExclusionGlobale>> getExclusionsByBranche(
            @PathVariable Branche branche
    ) {
        List<ExclusionGlobale> exclusions = exclusionGlobaleService.getExclusionsByBranche(branche);
        return ResponseEntity.ok(exclusions);
    }

    // 🔹 Supprimer une exclusion globale par ID (DELETE)
    @DeleteMapping("/exclusion-globale/{id}")
    public ResponseEntity<Void> deleteExclusionGlobale(
            @PathVariable UUID id,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        // 🔹 Récupération du token dans le header
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }

        // 🔹 Extraction du username depuis le token
        String username = jwtService.extractUserName(token);

        // 🔹 Suppression de l'exclusion globale
        exclusionGlobaleService.deleteExclusion(id);

        // 🔹 Enregistrement de l’historique
        historiqueContratService.enregistrerHistorique(
                "Suppression exclusion globale ID : " + id,
                username,
                0L // tempsRealisation
        );

        return ResponseEntity.noContent().build();
    }

}
