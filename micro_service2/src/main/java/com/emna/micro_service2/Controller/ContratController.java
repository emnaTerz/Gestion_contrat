package com.emna.micro_service2.Controller;


import com.emna.jwt_service.Service.JwtService;
import com.emna.micro_service2.Service.ContratPdfService;
import com.emna.micro_service2.Service.HistoriqueContratService;
import com.emna.micro_service2.dto.AdherentDTO;
import com.emna.micro_service2.dto.ContratDTO;
import com.emna.micro_service2.dto.Responses.ContratResponseDTO;
import com.emna.micro_service2.entities.Contrat;
import com.emna.micro_service2.Service.ContratService;
import com.emna.micro_service2.entities.HistoriqueContrat;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;


import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;


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
    @Autowired
    private ContratPdfService contratPdfService;



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
  /*  @GetMapping("/historique")
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
    }*/

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



   @GetMapping("/locked")
    public ResponseEntity<List<Contrat>> getLockedContrats(HttpServletRequest request) {
        try {
            // Extraire le token depuis l'en-tête Authorization
            String token = jwtService.getTokenFromRequest(request);
            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            // Extraire le username à partir du token
            String username = jwtService.extractUserName(token);
            if (username == null || username.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            // Charger UserDetails
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Vérifier que le token est valide
            if (!jwtService.isTokenValid(token, userDetails)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            // Récupérer tous les contrats verrouillés
            List<Contrat> lockedContrats = contratService.getLockedContrats();
            return ResponseEntity.ok(lockedContrats);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



  /*  @GetMapping("/{numPolice}/pdf")
    public ResponseEntity<byte[]> downloadContratPdf(@PathVariable String numPolice, HttpServletRequest request) {
        try {
            // Récupérer le contrat (votre méthode existante)
            ContratResponseDTO contrat = contratService.getContratComplet(numPolice, request);

            // Générer le PDF
            byte[] pdfBytes = contratPdfService.generateContratPdf(contrat);

            // Retourner le fichier PDF
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contrat_" + numPolice + ".pdf")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                    .body(pdfBytes);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/
  @GetMapping("/{numPolice}/pdf")
  public ResponseEntity<byte[]> downloadContratPdf(
          @PathVariable String numPolice,
          @RequestParam(required = false) String timestamp,
          HttpServletRequest request) {

      try {
          ContratResponseDTO contrat = contratService.getContratComplet(numPolice, request);
          byte[] pdfBytes = contratPdfService.generateContratPdf(contrat);

          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.APPLICATION_PDF);

          // Forcer le téléchargement avec un nom unique
          String filename = numPolice + "_" + System.currentTimeMillis() + ".pdf";
          headers.setContentDisposition(ContentDisposition.builder("attachment")
                  .filename(filename)
                  .build());

          // Désactiver le cache
          headers.setCacheControl(CacheControl.noCache().getHeaderValue());
          headers.setPragma("no-cache");
          headers.setExpires(0);

          return ResponseEntity.ok()
                  .headers(headers)
                  .body(pdfBytes);
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
  }}
