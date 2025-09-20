package com.emna.micro_service2.Controller;





import com.emna.micro_service2.Service.ClausierService;
import com.emna.micro_service2.Service.GarantieService;
import com.emna.micro_service2.Service.SousGarantieService;
import com.emna.micro_service2.Service.ExclusionService;
import com.emna.micro_service2.entities.Clausier;
import com.emna.micro_service2.entities.Garantie;
import com.emna.micro_service2.entities.SousGarantie;
import com.emna.micro_service2.entities.Exclusion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contrat/catalogue")
public class CatalogueController {

    private final GarantieService garantieService;
    private final SousGarantieService sousGarantieService;
    private final ExclusionService exclusionService;
    private final ClausierService clausierService;

    public CatalogueController(GarantieService garantieService, SousGarantieService sousGarantieService, ExclusionService exclusionService, ClausierService clausierService) {
        this.garantieService = garantieService;
        this.sousGarantieService = sousGarantieService;
        this.exclusionService = exclusionService;
        this.clausierService = clausierService;
    }

    // ---------------- Garanties ----------------
    @PostMapping("/garantie")
    public ResponseEntity<Garantie> createGarantie(@RequestBody Garantie garantie) {
        return ResponseEntity.ok(garantieService.createOrUpdate(garantie));
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
    public ResponseEntity<Void> deleteGarantie(@PathVariable Long id) {
        garantieService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ---------------- Sous-Garanties ----------------
    @PostMapping("/sous-garantie")
    public ResponseEntity<SousGarantie> createSousGarantie(@RequestBody SousGarantie sousGarantie) {
        return ResponseEntity.ok(sousGarantieService.createOrUpdate(sousGarantie));
    }

    @GetMapping("/sous-garantie")
    public ResponseEntity<List<SousGarantie>> getAllSousGaranties() {
        return ResponseEntity.ok(sousGarantieService.getAll());
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
    public ResponseEntity<Void> deleteSousGarantie(@PathVariable Long id) {
        sousGarantieService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ---------------- Exclusions ----------------
    @PostMapping("/exclusion")
    public ResponseEntity<Exclusion> createExclusion(@RequestBody Exclusion exclusion) {
        return ResponseEntity.ok(exclusionService.createOrUpdate(exclusion));
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

    // **Récupérer toutes les exclusions d’une garantie**
    @GetMapping("/exclusion/garantie/{garantieId}")
    public ResponseEntity<List<Exclusion>> getExclusionsByGarantie(@PathVariable Long garantieId) {
        return ResponseEntity.ok(exclusionService.getByGarantie(garantieId));
    }

    @DeleteMapping("/exclusion/{id}")
    public ResponseEntity<Void> deleteExclusion(@PathVariable Long id) {
        exclusionService.delete(id);
        return ResponseEntity.noContent().build();
    }
    // ---------------- Clausiers ----------------
    @PostMapping("/clausier")
    public ResponseEntity<Clausier> createClausier(@RequestBody Clausier clausier) {
        return ResponseEntity.ok(clausierService.createOrUpdate(clausier));
    }

    @GetMapping("/clausier")
    public ResponseEntity<List<Clausier>> getAllClausiers() {
        return ResponseEntity.ok(clausierService.getAll());
    }

    @GetMapping("/clausier/{id}")
    public ResponseEntity<Clausier> getClausierById(@PathVariable Long id) {
        return clausierService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer tous les clausiers d’une sous-garantie
    @GetMapping("/clausier/sous-garantie/{sousGarantieId}")
    public ResponseEntity<List<Clausier>> getClausiersBySousGarantie(@PathVariable Long sousGarantieId) {
        return ResponseEntity.ok(clausierService.getBySousGarantie(sousGarantieId));
    }

    @DeleteMapping("/clausier/{id}")
    public ResponseEntity<Void> deleteClausier(@PathVariable Long id) {
        clausierService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
