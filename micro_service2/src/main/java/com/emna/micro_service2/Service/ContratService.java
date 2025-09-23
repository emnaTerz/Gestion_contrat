package com.emna.micro_service2.Service;

import com.emna.micro_service2.Repository.*;
import com.emna.micro_service2.dto.ContratDTO;
import com.emna.micro_service2.dto.ExclusionGarantieDTO;
import com.emna.micro_service2.dto.GarantieSectionDTO;
import com.emna.micro_service2.dto.Responses.*;
import com.emna.micro_service2.dto.SectionDTO;
import com.emna.micro_service2.entities.*;
import com.emna.jwt_service.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContratService {

    @Autowired
    private ContratRepository contratRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private GarantieSectionRepository garantieSectionRepository;
    @Autowired
    private ExclusionGarantieRepository exclusionGarantieRepository;
    @Autowired
    private AdherentRepository adherentRepository;

    @Autowired
    private SectionService sectionService;
    @Autowired
    private GarantieSectionService garantieSectionService;
    @Autowired
    private ExclusionGarantieService exclusionGarantieService;
    @Autowired
    private HistoriqueContratService historiqueContratService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private SousGarantieRepository sousGarantieRepository;
    @Autowired
    private ExclusionRepository exclusionRepository;


    // ------------------- CREATION -------------------
    @Transactional
    public Contrat creerContratComplet(ContratDTO dto, HttpServletRequest request) throws Exception {
        // -------------------- START TIMER --------------------
        LocalDateTime startProcess = LocalDateTime.now();

        // -------------------- VALIDATION TOKEN --------------------
        String token = jwtService.getTokenFromRequest(request);
        if (token == null) throw new Exception("Token manquant");
        String username = jwtService.extractUserName(token);
        if (!jwtService.isTokenValid(token, username)) throw new Exception("Token invalide");

        // -------------------- VERIFICATION CONTRAT --------------------
        if (dto.getNumPolice() == null || contratRepository.existsById(dto.getNumPolice())) {
            throw new Exception("Numéro de police déjà existant ou manquant : " + dto.getNumPolice());
        }

        // -------------------- CREATION CONTRAT --------------------
        Contrat contrat = new Contrat();
        contrat.setNumPolice(dto.getNumPolice());
        contrat.setFractionnement(dto.getFractionnement());
        contrat.setCodeRenouvellement(dto.getCodeRenouvellement());
        contrat.setBranche(dto.getBranche());
        contrat.setNom_assure(dto.getNom_assure());
        contrat.setTypeContrat(dto.getTypeContrat());
        contrat.setPrimeTTC(dto.getPrimeTTC());
        contrat.setDateDebut(dto.getDateDebut());
        contrat.setDateFin(dto.getDateFin());
        contrat.setEditingUser(username);
        contrat.setEditingStart(LocalDateTime.now());

        // -------------------- CREATION ADHERENT --------------------
        if (dto.getAdherent() != null) {
            Adherent adherent = adherentRepository.findById(dto.getAdherent().getCodeId())
                    .orElseGet(() -> new Adherent(
                            dto.getAdherent().getCodeId(),
                            dto.getAdherent().getNomRaison(),
                            dto.getAdherent().getAdresse(),
                            dto.getAdherent().getActivite()
                    ));
            adherent.setNomRaison(dto.getAdherent().getNomRaison());
            adherent.setAdresse(dto.getAdherent().getAdresse());
            adherent.setActivite(dto.getAdherent().getActivite());
            adherentRepository.save(adherent);
            contrat.setAdherent(adherent);
        }

        contratRepository.save(contrat);

        // -------------------- CREATION SECTIONS, GARANTIES, EXCLUSIONS --------------------
        if (dto.getSections() != null) {
            for (SectionDTO sDTO : dto.getSections()) {
                Section section = new Section();
                section.setIdentification(sDTO.getIdentification());
                section.setAdresse(sDTO.getAdresse());
                section.setNatureConstruction(sDTO.getNatureConstruction());
                section.setContiguite(sDTO.getContiguite());
                section.setAvoisinage(sDTO.getAvoisinage());
                section.setContrat(contrat);
                sectionRepository.save(section);

                if (sDTO.getGaranties() != null) {
                    for (GarantieSectionDTO gDTO : sDTO.getGaranties()) {
                        GarantieSection gs = new GarantieSection();
                        gs.setSection(section);
                        gs.setSousGarantie(gDTO.getSousGarantieId() != null ? findSousGarantieById(gDTO.getSousGarantieId()) : null);
                        gs.setFranchise(gDTO.getFranchise());
                        gs.setLimite(gDTO.getLimite());
                        gs.setMaximum(gDTO.getMaximum());
                        gs.setMinimum(gDTO.getMinimum());
                        gs.setCapitale(gDTO.getCapitale());
                        gs.setPrimeNET(gDTO.getPrimeNET());
                        garantieSectionRepository.save(gs);

                        if (gDTO.getExclusions() != null) {
                            for (ExclusionGarantieDTO exDTO : gDTO.getExclusions()) {
                                ExclusionGarantie ex = new ExclusionGarantie();
                                ex.setGarantieSection(gs);
                                ex.setExclusion(findExclusionById(exDTO.getExclusionId()));
                                exclusionGarantieRepository.save(ex);
                            }
                        }
                    }
                }
            }
        }

        // -------------------- HISTORIQUE --------------------
        LocalDateTime start = dto.getStartTime(); // reçu du front
        long tempsRealisation = start != null
                ? Duration.between(start, LocalDateTime.now()).toMillis()
                : 0;


        historiqueContratService.enregistrerHistorique(
                "Création contrat complet " + contrat.getNumPolice(),
                username,
                tempsRealisation
        );

        return contrat;
    }

    @Transactional
    public Contrat modifierContrat(ContratDTO dto, HttpServletRequest request) throws Exception {
        String username = validateTokenAndGetUser(request);

        // 1️⃣ Récupérer le contrat existant
        Contrat contrat = contratRepository.findById(dto.getNumPolice())
                .orElseThrow(() -> new Exception("Contrat introuvable"));

        // 2️⃣ Vérifier verrou
        if (contrat.getEditingUser() != null && !contrat.getEditingUser().equals(username)) {
            throw new Exception("Ce contrat est actuellement modifié par un autre utilisateur : " + contrat.getEditingUser());
        }
        contrat.setEditingUser(username);
        contrat.setEditingStart(LocalDateTime.now());
        contratRepository.save(contrat);

        // 3️⃣ Mettre à jour les champs simples
        mapDtoToEntity(dto, contrat);
        contratRepository.save(contrat);

        // 4️⃣ Supprimer les anciennes sections, garanties et exclusions
        List<Section> anciennesSections = sectionRepository.findByContrat_NumPolice(contrat.getNumPolice());
        for (Section sec : anciennesSections) {
            List<GarantieSection> garanties = garantieSectionRepository.findBySection_Id(sec.getId());
            for (GarantieSection g : garanties) {
                List<ExclusionGarantie> exclusions = exclusionGarantieRepository.findByGarantieSection_Id(g.getId());
                exclusionGarantieRepository.deleteAll(exclusions);
            }
            garantieSectionRepository.deleteAll(garanties);
        }
        sectionRepository.deleteAll(anciennesSections);

        // 5️⃣ Créer les nouvelles sections et garanties
        for (SectionDTO sDto : dto.getSections()) {
            Section section = new Section();
            section.setIdentification(sDto.getIdentification());
            section.setAdresse(sDto.getAdresse());
            section.setNatureConstruction(sDto.getNatureConstruction());
            section.setContiguite(sDto.getContiguite());
            section.setAvoisinage(sDto.getAvoisinage());
            section.setContrat(contrat);
            sectionRepository.save(section);

            for (GarantieSectionDTO gDto : sDto.getGaranties()) {
                GarantieSection garantie = new GarantieSection();
                garantie.setFranchise(gDto.getFranchise());
                garantie.setSection(section);

                // ⚡ Récupérer l'entité SousGarantie depuis l'ID
                SousGarantie sousGarantie = sousGarantieRepository.findById(gDto.getSousGarantieId())
                        .orElseThrow(() -> new Exception("SousGarantie introuvable"));
                garantie.setSousGarantie(sousGarantie);

                garantie.setLimite(gDto.getLimite());
                garantie.setMaximum(gDto.getMaximum());
                garantie.setMinimum(gDto.getMinimum());
                garantie.setCapitale(gDto.getCapitale());
                garantie.setPrimeNET(gDto.getPrimeNET());
                garantie.setPrimeTTC(gDto.getPrimeTTC());
                garantieSectionRepository.save(garantie);

                // Exclusions
                for (ExclusionGarantieDTO eDto : gDto.getExclusions()) {
                    Exclusion exclusion = exclusionRepository.findById(eDto.getExclusionId())
                            .orElseThrow(() -> new Exception("Exclusion introuvable"));
                    ExclusionGarantie exG = new ExclusionGarantie();
                    exG.setGarantieSection(garantie);
                    exG.setExclusion(exclusion);
                    exclusionGarantieRepository.save(exG);
                }
            }
        }

        // 6️⃣ Historique
        logHistoriqueModification(contrat, username);

        // 7️⃣ Déverrouillage
        unlockContratInternal(contrat);

        return contrat;
    }

    // ------------------- LOCK / UNLOCK -------------------
   /* @Transactional
    public Contrat lockContrat(String numPolice, HttpServletRequest request) throws Exception {
        String token = jwtService.getTokenFromRequest(request);
        if (token == null) throw new Exception("Token manquant");

        String username = jwtService.extractUserName(token);

        // Vérifier si l’utilisateur a déjà un contrat locké
        Optional<Contrat> existingLock = contratRepository.findByEditingUser(username);
        if (existingLock.isPresent() && !existingLock.get().getNumPolice().equals(numPolice)) {
            throw new Exception("Vous avez déjà un contrat en cours de modification : "
                    + existingLock.get().getNumPolice());
        }

        Contrat contrat = contratRepository.findById(numPolice)
                .orElseThrow(() -> new Exception("Contrat introuvable"));

        if (contrat.getEditingUser() != null && !contrat.getEditingUser().equals(username)) {
            throw new Exception("Contrat déjà en cours de modification par un autre utilisateur");
        }

        contrat.setEditingUser(username);
        contrat.setEditingStart(LocalDateTime.now());
        return contratRepository.save(contrat);
    }*/

  /*  @Transactional
    public void unlockContrat(String numPolice, HttpServletRequest request, boolean cancelled, LocalDateTime startTime) throws Exception {
        String username = validateTokenAndGetUser(request);

        // Récupérer le contrat exact par son numéro
        Contrat contrat = contratRepository.findById(numPolice)
                .orElseThrow(() -> new Exception("Contrat introuvable"));

        // Calcul du temps basé sur le startTime envoyé par le front
        long tempsRealisation = startTime != null
                ? java.time.Duration.between(startTime, LocalDateTime.now()).toMillis()
                : 0;

        // Historique
        String action = cancelled
                ? "Tentative modification contrat " + contrat.getNumPolice() + " (annulée)"
                : "Fin modification contrat " + contrat.getNumPolice();

        historiqueContratService.enregistrerHistorique(action, username, tempsRealisation);

        // 🔹 Déverrouillage du contrat précis
        unlockContratInternal(contrat);
    }*/


    @Transactional
    public Contrat lockContrat(String numPolice, HttpServletRequest request) throws Exception {
        String token = jwtService.getTokenFromRequest(request);
        if (token == null) throw new Exception("Token manquant");

        String username = jwtService.extractUserName(token);

        // Vérifier si l’utilisateur a déjà un contrat locké
        List<Contrat> existingLocks = contratRepository.findByEditingUser(username);
        boolean hasOtherLock = existingLocks.stream()
                .anyMatch(c -> !c.getNumPolice().equals(numPolice));

        if (hasOtherLock) {
            throw new Exception("Vous avez déjà un contrat en cours de modification : "
                    + existingLocks.get(0).getNumPolice());
        }

        Contrat contrat = contratRepository.findById(numPolice)
                .orElseThrow(() -> new Exception("Contrat introuvable"));

        if (contrat.getEditingUser() != null && !contrat.getEditingUser().equals(username)) {
            throw new Exception("Contrat déjà en cours de modification par un autre utilisateur");
        }

        contrat.setEditingUser(username);
        contrat.setEditingStart(LocalDateTime.now());

        return contratRepository.save(contrat);
    }

    // ----------------------- UNLOCK -----------------------
    @Transactional
    public void unlockContrat(String numPolice, HttpServletRequest request, boolean cancelled, LocalDateTime startTime) throws Exception {
        String username = jwtService.extractUserName(jwtService.getTokenFromRequest(request));

        Contrat contrat = contratRepository.findById(numPolice)
                .orElseThrow(() -> new Exception("Contrat introuvable"));

        if (contrat.getEditingUser() == null || !contrat.getEditingUser().equals(username)) {
            throw new Exception("Vous ne pouvez pas déverrouiller ce contrat");
        }

        // Historique
        long tempsRealisation = startTime != null
                ? java.time.Duration.between(startTime, LocalDateTime.now()).toMillis()
                : 0;

        String action = cancelled
                ? "Tentative modification contrat " + contrat.getNumPolice() + " (annulée)"
                : "Fin modification contrat " + contrat.getNumPolice();

        historiqueContratService.enregistrerHistorique(action, username, tempsRealisation);

        // Déverrouillage
        contrat.setEditingUser(null);
        contrat.setEditingStart(null);
        contratRepository.save(contrat);
    }



    private void unlockContratInternal(Contrat contrat) {
        contrat.setEditingUser(null);
        contrat.setEditingStart(null);
        contratRepository.save(contrat);
    }

    // ------------------- GET / CHECK -------------------
    @Transactional(readOnly = true)
    public boolean isUnlocked(String numPolice) throws Exception {
        Contrat contrat = contratRepository.findById(numPolice)
                .orElseThrow(() -> new Exception("Contrat introuvable"));
        return contrat.getEditingUser() == null;
    }

    @Transactional(readOnly = true)
    public ContratResponseDTO getContratComplet(String numPolice, HttpServletRequest request) throws Exception {
        // Vérification et récupération de l'utilisateur
        String username = validateTokenAndGetUser(request);

        // Récupération du contrat
        Contrat contrat = contratRepository.findById(numPolice)
                .orElseThrow(() -> new Exception("Contrat introuvable avec numPolice : " + numPolice));

        // Mapping principal
        ContratResponseDTO contratDTO = new ContratResponseDTO();
        contratDTO.setNumPolice(contrat.getNumPolice());
        contratDTO.setFractionnement(contrat.getFractionnement());
        contratDTO.setCodeRenouvellement(contrat.getCodeRenouvellement());
        contratDTO.setBranche(contrat.getBranche());
        contratDTO.setTypeContrat(contrat.getTypeContrat());
        contratDTO.setPreambule(contrat.getPreambule());
        contratDTO.setPrimeTTC(contrat.getPrimeTTC());
        contratDTO.setNom_assure(contrat.getNom_assure());
        contratDTO.setDateDebut(contrat.getDateDebut());
        contratDTO.setDateFin(contrat.getDateFin());
        contratDTO.setEditingUser(contrat.getEditingUser());
        contratDTO.setEditingStart(contrat.getEditingStart());

        // Adhérent
        if (contrat.getAdherent() != null) {
            Adherent a = contrat.getAdherent();
            AdherentResponseDTO adherentDTO = new AdherentResponseDTO(
                    a.getCodeId(),
                    a.getNomRaison(),
                    a.getAdresse(),
                    a.getActivite()
            );
            contratDTO.setAdherent(adherentDTO);
        }

        // Sections
        List<Section> sections = sectionRepository.findByContrat_NumPolice(numPolice);
        List<SectionResponseDTO> sectionDTOs = new ArrayList<>();

        for (Section section : sections) {
            SectionResponseDTO sDTO = new SectionResponseDTO();
            sDTO.setId(section.getId());
            sDTO.setIdentification(section.getIdentification());
            sDTO.setAdresse(section.getAdresse());
            sDTO.setNatureConstruction(section.getNatureConstruction());
            sDTO.setContiguite(section.getContiguite());
            sDTO.setAvoisinage(section.getAvoisinage());
            sDTO.setNumPolice(numPolice);

            // Garanties
            List<GarantieSection> garanties = garantieSectionRepository.findBySection_Id(section.getId());
            List<GarantieSectionResponseDTO> gsDTOs = new ArrayList<>();
            for (GarantieSection gs : garanties) {
                GarantieSectionResponseDTO gsDTO = new GarantieSectionResponseDTO();
                gsDTO.setId(gs.getId());
                gsDTO.setSectionId(gs.getSection().getId());
                gsDTO.setSousGarantieId(gs.getSousGarantie() != null ? gs.getSousGarantie().getId() : null);
                gsDTO.setFranchise(gs.getFranchise());
                gsDTO.setLimite(gs.getLimite());
                gsDTO.setMaximum(gs.getMaximum());
                gsDTO.setMinimum(gs.getMinimum());
                gsDTO.setCapitale(gs.getCapitale());
                gsDTO.setPrimeNet(gs.getPrimeNET());

                // Exclusions
                List<ExclusionGarantie> exclusions = exclusionGarantieRepository.findByGarantieSection_Id(gs.getId());
                List<ExclusionGarantieResponseDTO> exDTOs = new ArrayList<>();
                for (ExclusionGarantie ex : exclusions) {
                    ExclusionGarantieResponseDTO exDTO = new ExclusionGarantieResponseDTO();
                    exDTO.setId(ex.getId());
                    exDTO.setGarantieSectionId(gs.getId());
                    exDTO.setExclusionId(ex.getExclusion() != null ? ex.getExclusion().getId() : null);
                    exDTOs.add(exDTO);
                }
                gsDTO.setExclusions(exDTOs);

                gsDTOs.add(gsDTO);
            }
            sDTO.setGaranties(gsDTOs);
            sectionDTOs.add(sDTO);
        }
        contratDTO.setSections(sectionDTOs);

        return contratDTO;
    }
    public boolean existsByNumPolice(String numPolice) {
        return contratRepository.existsByNumPolice(numPolice);
    }

    // ------------------- UTILITAIRES -------------------

    private String validateTokenAndGetUser(HttpServletRequest request) throws Exception {
        String token = jwtService.getTokenFromRequest(request);
        if (token == null) throw new Exception("Token manquant");
        String username = jwtService.extractUserName(token);
        if (!jwtService.isTokenValid(token, username)) throw new Exception("Token invalide");
        return username;
    }

    private Contrat getContratLockedByUser(String numPolice, String username) throws Exception {
        Contrat contrat = contratRepository.findById(numPolice)
                .orElseThrow(() -> new Exception("Contrat introuvable : " + numPolice));

        if (!username.equals(contrat.getEditingUser())) {
            throw new Exception("Vous n'avez pas verrouillé ce contrat");
        }
        return contrat;
    }

    private void logHistoriqueModification(Contrat contrat, String username) {
        LocalDateTime start = contrat.getEditingStart();
        long tempsRealisation = start != null
                ? java.time.Duration.between(start, LocalDateTime.now()).toMillis()
                : 0;

        historiqueContratService.enregistrerHistorique(
                "Modification contrat " + contrat.getNumPolice(),
                username,
                tempsRealisation
        );
    }

    private SousGarantie findSousGarantieById(Long id) throws Exception {
        return sousGarantieRepository.findById(id)
                .orElseThrow(() -> new Exception("SousGarantie introuvable avec id : " + id));
    }

    private Exclusion findExclusionById(Long id) throws Exception {
        return exclusionRepository.findById(id)
                .orElseThrow(() -> new Exception("Exclusion introuvable avec id : " + id));
    }

    // ------------------- MAPPING DTO ↔ ENTITY -------------------
    private void mapDtoToEntity(ContratDTO dto, Contrat contrat) {
        contrat.setNumPolice(dto.getNumPolice());
        contrat.setFractionnement(dto.getFractionnement());
        contrat.setCodeRenouvellement(dto.getCodeRenouvellement());
        contrat.setBranche(dto.getBranche());
        contrat.setTypeContrat(dto.getTypeContrat());
        contrat.setPrimeTTC(dto.getPrimeTTC());
        contrat.setDateDebut(dto.getDateDebut());
        contrat.setNom_assure(dto.getNom_assure());
        contrat.setDateFin(dto.getDateFin());
        contrat.setAdherent(dto.getAdherent() != null ? mapAdherentDtoToEntity(dto.getAdherent()) : null);
    }

    private Adherent mapAdherentDtoToEntity(com.emna.micro_service2.dto.AdherentDTO dto) {
        Adherent adherent = new Adherent();
        adherent.setCodeId(dto.getCodeId());
        adherent.setNomRaison(dto.getNomRaison());
        adherent.setAdresse(dto.getAdresse());
        adherent.setActivite(dto.getActivite());
        return adherent;
    }

    private ContratResponseDTO mapContratToResponseDTO(Contrat contrat) {
        ContratResponseDTO contratDTO = new ContratResponseDTO();
        contratDTO.setNumPolice(contrat.getNumPolice());
        contratDTO.setFractionnement(contrat.getFractionnement());
        contratDTO.setCodeRenouvellement(contrat.getCodeRenouvellement());
        contratDTO.setBranche(contrat.getBranche());
        contratDTO.setTypeContrat(contrat.getTypeContrat());
        contratDTO.setPreambule(contrat.getPreambule());
        contratDTO.setPrimeTTC(contrat.getPrimeTTC());
        contratDTO.setNom_assure(contratDTO.getNom_assure());
        contratDTO.setDateDebut(contrat.getDateDebut());
        contratDTO.setDateFin(contrat.getDateFin());
        contratDTO.setEditingUser(contrat.getEditingUser());
        contratDTO.setEditingStart(contrat.getEditingStart());

        if (contrat.getAdherent() != null) {
            Adherent a = contrat.getAdherent();
            contratDTO.setAdherent(new AdherentResponseDTO(
                    a.getCodeId(), a.getNomRaison(), a.getAdresse(), a.getActivite()
            ));
        }

        // Sections / garanties / exclusions
        List<Section> sections = sectionRepository.findByContrat_NumPolice(contrat.getNumPolice());
        List<SectionResponseDTO> sectionDTOs = new ArrayList<>();
        for (Section section : sections) {
            SectionResponseDTO sDTO = new SectionResponseDTO();
            sDTO.setId(section.getId());
            sDTO.setIdentification(section.getIdentification());
            sDTO.setAdresse(section.getAdresse());
            sDTO.setNatureConstruction(section.getNatureConstruction());
            sDTO.setContiguite(section.getContiguite());
            sDTO.setAvoisinage(section.getAvoisinage());
            sDTO.setNumPolice(contrat.getNumPolice());

            List<GarantieSection> garanties = garantieSectionRepository.findBySection_Id(section.getId());
            List<GarantieSectionResponseDTO> gsDTOs = new ArrayList<>();
            for (GarantieSection gs : garanties) {
                GarantieSectionResponseDTO gsDTO = new GarantieSectionResponseDTO();
                gsDTO.setId(gs.getId());
                gsDTO.setSectionId(gs.getSection().getId());
                gsDTO.setSousGarantieId(gs.getSousGarantie() != null ? gs.getSousGarantie().getId() : null);
                gsDTO.setFranchise(gs.getFranchise());
                gsDTO.setLimite(gs.getLimite());
                gsDTO.setMaximum(gs.getMaximum());
                gsDTO.setMinimum(gs.getMinimum());
                gsDTO.setCapitale(gs.getCapitale());
                gsDTO.setPrimeNet(gs.getPrimeNET());

                List<ExclusionGarantie> exclusions = exclusionGarantieRepository.findByGarantieSection_Id(gs.getId());
                List<ExclusionGarantieResponseDTO> exDTOs = new ArrayList<>();
                for (ExclusionGarantie ex : exclusions) {
                    exDTOs.add(new ExclusionGarantieResponseDTO(
                            ex.getId(),
                            gs.getId(),
                            ex.getExclusion() != null ? ex.getExclusion().getId() : null
                    ));
                }
                gsDTO.setExclusions(exDTOs);
                gsDTOs.add(gsDTO);
            }
            sDTO.setGaranties(gsDTOs);
            sectionDTOs.add(sDTO);
        }
        contratDTO.setSections(sectionDTOs);

        return contratDTO;
    }
}
