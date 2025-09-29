package com.emna.micro_service2.Controller;



import com.emna.micro_service2.Service.TarifService;
import com.emna.micro_service2.entities.Tarif;

import com.emna.micro_service2.entities.enums.Branche;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contrat/tarifs")
public class TarifController {

    private final TarifService tarifService;

    public TarifController(TarifService tarifService) {
        this.tarifService = tarifService;
    }

    // ------------------ CREATE ------------------
    @PostMapping
    public ResponseEntity<?> createTarif(@RequestBody Tarif tarif, HttpServletRequest request) {
        try {
            Tarif created = tarifService.createTarif(tarif, request);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ------------------ UPDATE ------------------
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTarif(@PathVariable Long id, @RequestBody Tarif tarif, HttpServletRequest request) {
        try {
            Tarif updated = tarifService.updateTarif(id, tarif, request);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ------------------ GET ALL ------------------
    @GetMapping
    public ResponseEntity<?> getAllTarifs(HttpServletRequest request) {
        try {
            List<Tarif> tarifs = tarifService.getAllTarifs(request);
            return ResponseEntity.ok(tarifs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ------------------ GET BY BRANCHE ------------------
    @GetMapping("/{branche}")
    public ResponseEntity<?> getTarifByBranche(@PathVariable Branche branche) {
        try {
            Tarif tarif = tarifService.getTarifByBranche(branche);
            return ResponseEntity.ok(tarif);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
