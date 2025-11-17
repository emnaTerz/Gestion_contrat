package com.emna.micro_service2.Service;

import com.emna.micro_service2.Repository.*;
import com.emna.micro_service2.dto.*;
import com.emna.micro_service2.dto.Responses.*;
import com.emna.micro_service2.entities.*;
import com.emna.jwt_service.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private RC_ExploitationRepository rcExploitationRepository;

    @Autowired
    private ExclusionRCRepository exclusionRCRepository;
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


    @Autowired
    private TarifService TarifService;


    @Transactional
    public Contrat creerContratComplet(ContratDTO dto, HttpServletRequest request) throws Exception {
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
        contrat.setNom_assure(dto.getNom_assure() != null ? dto.getNom_assure() : "Mutuelle assurance de l'éducation MAE");
        contrat.setTypeContrat(dto.getTypeContrat());
        contrat.setPrimeTTC(dto.getPrimeTTC());
        contrat.setDateDebut(dto.getDateDebut());
        contrat.setDateFin(dto.getDateFin());
        contrat.setCodeAgence(dto.getCodeAgence());
        contrat.setPreambule(dto.getPreambule());
        contrat.setService(dto.getService());
        contrat.setStatus("figé");

        // -------------------- CREATION ADHERENT --------------------
        if (dto.getAdherent() != null) {
            Adherent adherent = adherentRepository.findById(dto.getAdherent().getCodeId())
                    .orElseGet(() -> new Adherent(
                            dto.getAdherent().getCodeId(),
                            dto.getAdherent().getNomRaison(),
                            dto.getAdherent().getAdresse(),
                            dto.getAdherent().isNouveau(),
                            dto.getAdherent().getActivite()
                    ));

            adherent.setNomRaison(dto.getAdherent().getNomRaison());
            adherent.setAdresse(dto.getAdherent().getAdresse());
            adherent.setActivite(dto.getAdherent().getActivite());
            adherent.setNouveau(dto.getAdherent().isNouveau());

            adherentRepository.save(adherent);
            contrat.setAdherent(adherent);
        }

        contratRepository.save(contrat);

        // -------------------- CREATION SECTIONS --------------------
        List<Section> sectionsSauvegardees = new ArrayList<>();
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
                sectionsSauvegardees.add(section);

                // Garanties
                if (sDTO.getGaranties() != null) {
                    for (GarantieSectionDTO gDTO : sDTO.getGaranties()) {
                        GarantieSection gs = new GarantieSection();
                        gs.setSection(section);
                        gs.setSousGarantie(gDTO.getSousGarantieId() != null ? findSousGarantieById(gDTO.getSousGarantieId()) : null);
                        gs.setFranchise(gDTO.getFranchise());
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

        // -------------------- CREATION RC EXPLOITATION --------------------
        if (dto.getRcConfigurations() != null) {
            int rcIndex = 0;
            for (RcConfigurationDTO rcDTO : dto.getRcConfigurations()) {
                RC_Exploitation rc = new RC_Exploitation();
                rc.setObjetDeLaGarantie(rcDTO.getObjetDeLaGarantie());
                rc.setLimiteAnnuelleDomCorporels(rcDTO.getLimiteAnnuelleDomCorporels() != null ? rcDTO.getLimiteAnnuelleDomCorporels() : 0.0);
                rc.setLimiteAnnuelleDomMateriels(rcDTO.getLimiteAnnuelleDomMateriels() != null ? rcDTO.getLimiteAnnuelleDomMateriels() : 0.0);
                rc.setLimiteParSinistre(rcDTO.getLimiteParSinistre() != null ? rcDTO.getLimiteParSinistre() : 0.0);
                rc.setFranchise(rcDTO.getFranchise() != null ? rcDTO.getFranchise() : 0.0);
                rc.setPrimeNET(rcDTO.getPrimeNET() != null ? rcDTO.getPrimeNET() : 0.0);
                rc.setContrat(contrat);

                // Exclusions RC
                if (rcDTO.getExclusionsRcIds() != null) {
                    List<ExclusionRC> exclusions = new ArrayList<>();
                    for (Long exId : rcDTO.getExclusionsRcIds()) {
                        ExclusionRC ex = findExclusionRCById(exId);
                        if (ex != null) exclusions.add(ex);
                    }
                    rc.setExclusionsRc(exclusions);
                }

                rcExploitationRepository.save(rc);

                // Associer RC aux sections selon un champ rcIndex ou logique personnalisée
                for (int i = 0; i < sectionsSauvegardees.size(); i++) {
                    Section section = sectionsSauvegardees.get(i);

                    // Exemple : les 2 premières sections au RC 1, la 3ème au RC 2
                    if ((rcIndex == 0 && i < 2) || (rcIndex == 1 && i == 2)) {
                        section.setRcExploitation(rc);
                        sectionRepository.save(section);
                    }
                }
                rcIndex++;
            }
        }
        LocalDateTime start = dto.getStartTime(); // reçu du front
        long tempsRealisation = start != null
                ? Duration.between(start, LocalDateTime.now()).toMillis()
                : 0;

        historiqueContratService.enregistrerHistorique(
                "Création contrat complet " + contrat.getNumPolice(),
                username,
                tempsRealisation
        );
        // Calcul prime
        Tarif tarif = TarifService.getTarifByBranche(contrat.getBranche());
        contrat.setPrimeTTC(calculerPrimeTTC(contrat, tarif));
        contrat.setPrimeNET(calculerSommeTotalePrimesNettes(sectionsSauvegardees));
        contratRepository.save(contrat);

        return contrat;
    }
    @Transactional
    public String getContratStatus(String numPolice) {
        Optional<Contrat> contratOpt = contratRepository.findById(numPolice);
        if (contratOpt.isPresent()) {
            return contratOpt.get().getStatus();
        } else {
            return "Contrat non trouvé";
        }
    }
    public Contrat toggleStatus(String numPolice) {
        Optional<Contrat> opt = contratRepository.findById(numPolice);
        if (opt.isPresent()) {
            Contrat contrat = opt.get();
            // Basculer le statut
            if ("figé".equalsIgnoreCase(contrat.getStatus())) {
                contrat.setStatus("actif");
            } else {
                contrat.setStatus("figé");
            }
            return contratRepository.save(contrat);
        }
        return null;
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

        // 4️⃣ Mettre à jour l'adhérent
        AdherentDTO aDto = dto.getAdherent();
        if (aDto != null) {
            Adherent adherent = contrat.getAdherent();
            if (adherent == null) {
                adherent = new Adherent();
                contrat.setAdherent(adherent);
            }
            adherent.setCodeId(aDto.getCodeId());
            adherent.setNomRaison(aDto.getNomRaison());
            adherent.setAdresse(aDto.getAdresse());
            adherent.setActivite(aDto.getActivite());
            adherent.setNouveau(aDto.isNouveau());
            adherentRepository.save(adherent);
        }

        // 5️⃣ Gestion intelligente des sections - basée sur l'identification
        List<Section> sectionsExistantes = sectionRepository.findByContrat_NumPolice(contrat.getNumPolice());
        Map<String, Section> sectionsParIdentification = sectionsExistantes.stream()
                .collect(Collectors.toMap(Section::getIdentification, s -> s));
        List<Section> sectionsSauvegardees = new ArrayList<>();

        // 6️⃣ Traiter chaque section du DTO
        for (SectionDTO sDto : dto.getSections()) {
            Section section;

            // Vérifier si la section existe déjà par son identification
            if (sectionsParIdentification.containsKey(sDto.getIdentification())) {
                // Mettre à jour la section existante
                section = sectionsParIdentification.get(sDto.getIdentification());
                sectionsParIdentification.remove(sDto.getIdentification()); // Retirer de la map
            } else {
                // Créer une nouvelle section
                section = new Section();
                section.setContrat(contrat);
            }

            // Mettre à jour les champs de la section
            section.setIdentification(sDto.getIdentification());
            section.setAdresse(sDto.getAdresse());
            section.setNatureConstruction(sDto.getNatureConstruction());
            section.setContiguite(sDto.getContiguite());
            section.setAvoisinage(sDto.getAvoisinage());
            sectionRepository.save(section);

            // 7️⃣ Gestion des garanties classiques - basée sur la sous-garantie
            traiterGarantiesSection(section, sDto.getGaranties());
            sectionsSauvegardees.add(section);
        }

        // 8️⃣ Supprimer les sections qui n'existent plus dans le DTO
        if (!sectionsParIdentification.isEmpty()) {
            for (Section sectionASupprimer : sectionsParIdentification.values()) {
                supprimerSectionAvecDependances(sectionASupprimer);
            }
        }

        // 9️⃣ Gestion des RC Configurations au niveau racine (NOUVEAU)
        if (dto.getRcConfigurations() != null && !dto.getRcConfigurations().isEmpty()) {
            traiterRcConfigurationsRacine(dto.getRcConfigurations(), contrat);
        } else {
            // Si pas de RC configurations, supprimer tous les RC existants
            supprimerTousLesRC(contrat);
        }

        // 🔟 Calcul de la prime
        Tarif tarif = TarifService.getTarifByBranche(contrat.getBranche());
        contrat.setPrimeNET(calculerSommeTotalePrimesNettes(sectionsSauvegardees));
        contrat.setPrimeTTC(calculerPrimeTTC(contrat, tarif));

        contratRepository.save(contrat);

        unlockContrat(contrat.getNumPolice(), request, false, dto.getStartTime());
        // Déverrouillage
        unlockContratInternal(contrat);

        return contrat;
    }

    // NOUVELLE méthode pour traiter les RC Configurations au niveau racine
// NOUVELLE méthode pour traiter les RC Configurations au niveau racine
    private void traiterRcConfigurationsRacine(List<RcConfigurationDTO> rcConfigurations, Contrat contrat) throws Exception {
        // 1️⃣ D'abord, dissocier tous les RC existants des sections
        supprimerTousLesRC(contrat);

        // 2️⃣ Récupérer toutes les sections du contrat (avec leurs nouveaux IDs)
        List<Section> toutesLesSections = sectionRepository.findByContrat_NumPolice(contrat.getNumPolice());
        Map<String, Section> sectionsParIdentification = toutesLesSections.stream()
                .collect(Collectors.toMap(Section::getIdentification, s -> s));

        // 3️⃣ Créer les nouveaux RC et les associer aux sections
        for (RcConfigurationDTO rcConfig : rcConfigurations) {
            RC_Exploitation rc = new RC_Exploitation();
            rc.setObjetDeLaGarantie(rcConfig.getObjetDeLaGarantie());
            rc.setLimiteAnnuelleDomCorporels(rcConfig.getLimiteAnnuelleDomCorporels());
            rc.setLimiteAnnuelleDomMateriels(rcConfig.getLimiteAnnuelleDomMateriels());
            rc.setLimiteParSinistre(rcConfig.getLimiteParSinistre());
            rc.setFranchise(rcConfig.getFranchise());
            rc.setPrimeNET(rcConfig.getPrimeNET());

            rcExploitationRepository.save(rc);

            // Gestion des exclusions RC
            if (rcConfig.getExclusionsRcIds() != null) {
                List<ExclusionRC> exclusions = new ArrayList<>();
                for (Long exId : rcConfig.getExclusionsRcIds()) {
                    ExclusionRC ex = exclusionRCRepository.findById(exId)
                            .orElseThrow(() -> new Exception("Exclusion RC introuvable: " + exId));
                    exclusions.add(ex);
                }
                rc.setExclusionsRc(exclusions);
                rcExploitationRepository.save(rc);
            }

            // 🔥 CORRECTION : Utiliser les identifications au lieu des IDs
            // Associer le RC aux sections par identification (plus fiable)
            if (rcConfig.getSectionIdentifications() != null && !rcConfig.getSectionIdentifications().isEmpty()) {
                for (String identification : rcConfig.getSectionIdentifications()) {
                    Section section = sectionsParIdentification.get(identification);
                    if (section != null) {
                        section.setRcExploitation(rc);
                        sectionRepository.save(section);
                        System.out.println("✅ RC associé à la section: " + identification + " (ID: " + section.getId() + ")");
                    } else {
                        throw new Exception("Section introuvable avec l'identification: " + identification);
                    }
                }
            }
            // Si sectionIds est fourni, essayer de les utiliser (avec vérification)
            else if (rcConfig.getSectionIds() != null && !rcConfig.getSectionIds().isEmpty()) {
                for (Long sectionId : rcConfig.getSectionIds()) {
                    try {
                        Section section = sectionRepository.findById(sectionId)
                                .orElseThrow(() -> new Exception("Section introuvable avec ID: " + sectionId));
                        // Vérifier que la section appartient bien à ce contrat
                        if (!section.getContrat().getNumPolice().equals(contrat.getNumPolice())) {
                            throw new Exception("La section " + sectionId + " n'appartient pas au contrat " + contrat.getNumPolice());
                        }
                        section.setRcExploitation(rc);
                        sectionRepository.save(section);
                        System.out.println("✅ RC associé à la section ID: " + sectionId);
                    } catch (Exception e) {
                        System.err.println("❌ Erreur avec sectionId " + sectionId + ": " + e.getMessage());
                        throw e;
                    }
                }
            }
        }
    }

    // NOUVELLE méthode pour supprimer tous les RC d'un contrat
    private void supprimerTousLesRC(Contrat contrat) {
        List<Section> sectionsContrat = sectionRepository.findByContrat_NumPolice(contrat.getNumPolice());

        // Collecter tous les RC uniques
        Set<RC_Exploitation> rcASupprimer = new HashSet<>();

        for (Section section : sectionsContrat) {
            if (section.getRcExploitation() != null) {
                rcASupprimer.add(section.getRcExploitation());
                section.setRcExploitation(null);
                sectionRepository.save(section);
            }
        }

        // Supprimer les RC qui ne sont plus utilisés
        for (RC_Exploitation rc : rcASupprimer) {
            List<Section> sectionsUtilisantRC = sectionRepository.findByRcExploitation_Id(rc.getId());
            if (sectionsUtilisantRC.isEmpty()) {
                rcExploitationRepository.delete(rc);
            }
        }
    }

    // Méthode pour traiter les garanties d'une section (basée sur sousGarantieId)
    private void traiterGarantiesSection(Section section, List<GarantieSectionDTO> garantiesDTO) throws Exception {
        if (garantiesDTO == null) return;

        // Récupérer les garanties existantes
        List<GarantieSection> garantiesExistantes = garantieSectionRepository.findBySection_Id(section.getId());
        Map<Long, GarantieSection> garantiesParSousGarantieId = garantiesExistantes.stream()
                .collect(Collectors.toMap(g -> g.getSousGarantie().getId(), g -> g));

        for (GarantieSectionDTO gDto : garantiesDTO) {
            GarantieSection garantie;

            // Utiliser sousGarantieId comme clé pour identifier les garanties
            if (garantiesParSousGarantieId.containsKey(gDto.getSousGarantieId())) {
                // Mettre à jour garantie existante
                garantie = garantiesParSousGarantieId.get(gDto.getSousGarantieId());
                garantiesParSousGarantieId.remove(gDto.getSousGarantieId());
            } else {
                // Nouvelle garantie
                garantie = new GarantieSection();
                garantie.setSection(section);
                garantie.setSousGarantie(sousGarantieRepository.findById(gDto.getSousGarantieId())
                        .orElseThrow(() -> new Exception("SousGarantie introuvable")));
            }

            // Mettre à jour les champs
            garantie.setFranchise(gDto.getFranchise());
            garantie.setMaximum(gDto.getMaximum());
            garantie.setMinimum(gDto.getMinimum());
            garantie.setCapitale(gDto.getCapitale());
            garantie.setPrimeNET(gDto.getPrimeNET());
            garantie.setPrimeTTC(gDto.getPrimeTTC());
            garantieSectionRepository.save(garantie);

            // Gestion des exclusions
            traiterExclusionsGarantie(garantie, gDto.getExclusions());
        }

        // Supprimer les garanties qui n'existent plus
        if (!garantiesParSousGarantieId.isEmpty()) {
            for (GarantieSection garantieASupprimer : garantiesParSousGarantieId.values()) {
                supprimerGarantieAvecExclusions(garantieASupprimer);
            }
        }
    }

    // Méthode pour supprimer une section avec toutes ses dépendances
    private void supprimerSectionAvecDependances(Section section) {
        // Supprimer les garanties et leurs exclusions
        List<GarantieSection> garanties = garantieSectionRepository.findBySection_Id(section.getId());
        for (GarantieSection garantie : garanties) {
            supprimerGarantieAvecExclusions(garantie);
        }

        // Gérer le RC
        RC_Exploitation rc = section.getRcExploitation();
        if (rc != null) {
            section.setRcExploitation(null);
            sectionRepository.save(section);

            // Vérifier si le RC n'est plus utilisé
            List<Section> sectionsUtilisantRC = sectionRepository.findByRcExploitation_Id(rc.getId());
            if (sectionsUtilisantRC.isEmpty()) {
                rcExploitationRepository.delete(rc);
            }
        }

        // Supprimer la section
        sectionRepository.delete(section);
    }

    // Méthode pour supprimer une garantie avec ses exclusions
    private void supprimerGarantieAvecExclusions(GarantieSection garantie) {
        List<ExclusionGarantie> exclusions = exclusionGarantieRepository.findByGarantieSection_Id(garantie.getId());
        exclusionGarantieRepository.deleteAll(exclusions);
        garantieSectionRepository.delete(garantie);
    }

    // Méthode pour traiter les exclusions d'une garantie
    private void traiterExclusionsGarantie(GarantieSection garantie, List<ExclusionGarantieDTO> exclusionsDTO) throws Exception {
        if (exclusionsDTO == null) return;

        // Récupérer les exclusions existantes
        List<ExclusionGarantie> exclusionsExistantes = exclusionGarantieRepository.findByGarantieSection_Id(garantie.getId());
        Set<Long> exclusionIdsExistantes = exclusionsExistantes.stream()
                .map(e -> e.getExclusion().getId())
                .collect(Collectors.toSet());

        Set<Long> exclusionIdsDTO = exclusionsDTO.stream()
                .map(ExclusionGarantieDTO::getExclusionId)
                .collect(Collectors.toSet());

        // Supprimer les exclusions qui ne sont plus dans le DTO
        for (ExclusionGarantie exclusionExistante : exclusionsExistantes) {
            if (!exclusionIdsDTO.contains(exclusionExistante.getExclusion().getId())) {
                exclusionGarantieRepository.delete(exclusionExistante);
            }
        }

        // Ajouter les nouvelles exclusions
        for (Long exclusionId : exclusionIdsDTO) {
            if (!exclusionIdsExistantes.contains(exclusionId)) {
                Exclusion exclusion = exclusionRepository.findById(exclusionId)
                        .orElseThrow(() -> new Exception("Exclusion introuvable"));

                ExclusionGarantie nouvelleExclusion = new ExclusionGarantie();
                nouvelleExclusion.setGarantieSection(garantie);
                nouvelleExclusion.setExclusion(exclusion);
                exclusionGarantieRepository.save(nouvelleExclusion);
            }
        }
    }

    public List<Contrat> getAllContrats(HttpServletRequest request)throws Exception {
        // -------------------- VALIDATION TOKEN --------------------
        String token = jwtService.getTokenFromRequest(request);
        if (token == null) throw new Exception("Token manquant");
        String username = jwtService.extractUserName(token);
        if (!jwtService.isTokenValid(token, username)) throw new Exception("Token invalide");
        String action = "Consultation des contrats";
        historiqueContratService.enregistrerHistorique(action, username, 0L);
        return contratRepository.findAll();

    }
    // ------------------- LOCK / UNLOCK -------------------

    @Transactional(readOnly = true)
    public List<Contrat> getLockedContrats() {
        return contratRepository.findByEditingUserIsNotNull();
    }

    @Transactional
    public Contrat lockContrat(String numPolice, HttpServletRequest request) throws Exception {
        String token = jwtService.getTokenFromRequest(request);
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token manquant");
        }

        String username = jwtService.extractUserName(token);

        // Vérifier si l’utilisateur a déjà un contrat verrouillé
        List<Contrat> existingLocks = contratRepository.findByEditingUser(username);
        boolean hasOtherLock = existingLocks.stream()
                .anyMatch(c -> !c.getNumPolice().equals(numPolice));

        if (hasOtherLock) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Vous avez déjà un contrat en cours de modification : " + existingLocks.get(0).getNumPolice()
            );
        }

        Contrat contrat = contratRepository.findById(numPolice)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contrat introuvable"));

        if (contrat.getEditingUser() != null && !contrat.getEditingUser().equals(username)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ce contrat est déjà verrouillé par un autre utilisateur"
            );
        }

        // Verrouillage du contrat
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

        // Le propriétaire est celui qui a verrouillé le contrat
        boolean isOwner = username.equals(contrat.getEditingUser());

        // Autorisation spéciale pour Ismail.Jebari
        boolean isIsmail = "Ismail.Jebari".equals(username);

        // Vérification des droits
        if (!isOwner && !isIsmail) {
            throw new Exception("Vous ne pouvez pas déverrouiller ce contrat. Verrouillé par: " + contrat.getEditingUser());
        }

        // Calcul du temps de réalisation
        long tempsRealisation = (startTime != null)
                ? java.time.Duration.between(startTime, LocalDateTime.now()).toMillis()
                : 0;
        System.out.println("temps realisation"+java.time.Duration.between(startTime, LocalDateTime.now()).toMillis() );
        String action = cancelled
                ? "Tentative modification contrat " + contrat.getNumPolice() + " (annulée)"
                : "Fin modification contrat " + contrat.getNumPolice();


        if (isIsmail && !isOwner) {
            action += " (déverrouillé par Ismail)";
        }
        String user = contrat.getEditingUser();
        historiqueContratService.enregistrerHistorique(action, user, tempsRealisation);

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


    @Transactional
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
        contratDTO.setCodeAgence(contrat.getCodeAgence());
        contratDTO.setService(contrat.getService());
        contratDTO.setPrimeNET(contrat.getPrimeNET());
        // Adhérent
        if (contrat.getAdherent() != null) {
            Adherent a = contrat.getAdherent();
            AdherentResponseDTO adherentDTO = new AdherentResponseDTO(
                    a.getCodeId(),
                    a.getNomRaison(),
                    a.getAdresse(),
                    a.getActivite(),
                    a.isNouveau()
            );
            contratDTO.setAdherent(adherentDTO);
        }

        // Sections
        List<Section> sections = sectionRepository.findByContrat_NumPolice(numPolice);
        List<SectionResponseDTO> sectionDTOs = new ArrayList<>();

        // ✅ NOUVEAU: Map pour regrouper les RC par configuration
        Map<RC_Exploitation, List<Section>> rcConfigurationsMap = new HashMap<>();

        for (Section section : sections) {
            SectionResponseDTO sDTO = new SectionResponseDTO();
            sDTO.setId(section.getId());
            sDTO.setIdentification(section.getIdentification());
            sDTO.setAdresse(section.getAdresse());
            sDTO.setNatureConstruction(section.getNatureConstruction());
            sDTO.setContiguite(section.getContiguite());
            sDTO.setAvoisinage(section.getAvoisinage());
            sDTO.setNumPolice(numPolice);

            // ---------------- RC Exploitation ----------------
            RC_Exploitation rc = section.getRcExploitation();
            if (rc != null) {
                // ✅ Ajouter la section à la configuration RC correspondante
                rcConfigurationsMap.computeIfAbsent(rc, k -> new ArrayList<>()).add(section);
                sDTO.setRcConfigurationId(rc.getId()); // Optionnel: stocker l'ID de la RC
            }

            // ---------------- Garanties ----------------
            List<GarantieSection> garanties = garantieSectionRepository.findBySection_Id(section.getId());
            List<GarantieSectionResponseDTO> gsDTOs = new ArrayList<>();
            for (GarantieSection gs : garanties) {
                GarantieSectionResponseDTO gsDTO = new GarantieSectionResponseDTO();
                gsDTO.setId(gs.getId());
                gsDTO.setSectionId(gs.getSection().getId());
                gsDTO.setSousGarantieId(gs.getSousGarantie() != null ? gs.getSousGarantie().getId() : null);
                gsDTO.setFranchise(gs.getFranchise());
                gsDTO.setMaximum(gs.getMaximum());
                gsDTO.setMinimum(gs.getMinimum());
                gsDTO.setCapitale(gs.getCapitale());
                gsDTO.setPrimeNet(gs.getPrimeNET());

                if (gs.getSousGarantie() != null && gs.getSousGarantie().getGarantie() != null) {
                    Garantie garantieParent = gs.getSousGarantie().getGarantie();
                    GarantieResponseDTO garantieParentDTO = new GarantieResponseDTO();
                    garantieParentDTO.setId(garantieParent.getId());
                    garantieParentDTO.setLibelle(garantieParent.getLibelle());
                    gsDTO.setGarantieParent(garantieParentDTO);
                }

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

        // ✅ NOUVEAU: Construction des configurations RC
        List<RcConfigurationResponseDTO> rcConfigDTOs = new ArrayList<>();

        for (Map.Entry<RC_Exploitation, List<Section>> entry : rcConfigurationsMap.entrySet()) {
            RC_Exploitation rc = entry.getKey();
            List<Section> rcSections = entry.getValue();

            RcConfigurationResponseDTO rcConfigDTO = new RcConfigurationResponseDTO();
            rcConfigDTO.setId(rc.getId());
            rcConfigDTO.setLimiteAnnuelleDomCorporels(rc.getLimiteAnnuelleDomCorporels());
            rcConfigDTO.setLimiteAnnuelleDomMateriels(rc.getLimiteAnnuelleDomMateriels());
            rcConfigDTO.setLimiteParSinistre(rc.getLimiteParSinistre());
            rcConfigDTO.setFranchise(rc.getFranchise());
            rcConfigDTO.setPrimeNET(rc.getPrimeNET());
            rcConfigDTO.setObjetDeLaGarantie(rc.getObjetDeLaGarantie());

            // IDs des exclusions RC
            List<Long> exclusionIds = new ArrayList<>();
            if (rc.getExclusionsRc() != null) {
                for (ExclusionRC exRC : rc.getExclusionsRc()) {
                    exclusionIds.add(exRC.getId());
                }
            }
            rcConfigDTO.setExclusionsRcIds(exclusionIds);

            // IDs et noms des sections
            List<Long> sectionIds = new ArrayList<>();
            List<String> sectionIdentifications = new ArrayList<>();
            for (Section section : rcSections) {
                sectionIds.add(section.getId());
                sectionIdentifications.add(section.getIdentification());
            }
            rcConfigDTO.setSectionIds(sectionIds);
            rcConfigDTO.setSectionIdentifications(sectionIdentifications);

            rcConfigDTOs.add(rcConfigDTO);
        }

        contratDTO.setRcConfigurations(rcConfigDTOs);

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



    private SousGarantie findSousGarantieById(Long id) throws Exception {
        return sousGarantieRepository.findById(id)
                .orElseThrow(() -> new Exception("SousGarantie introuvable avec id : " + id));
    }

    private Exclusion findExclusionById(Long id) throws Exception {
        return exclusionRepository.findById(id)
                .orElseThrow(() -> new Exception("Exclusion introuvable avec id : " + id));
    }
    private ExclusionRC findExclusionRCById(Long id) throws Exception {
        return exclusionRCRepository.findById(id)
                .orElseThrow(() -> new Exception("ExclusionRC introuvable pour l'id : " + id));
    }

    // ------------------- MAPPING DTO ↔ ENTITY -------------------
    private void mapDtoToEntity(ContratDTO dto, Contrat contrat) {
        contrat.setNumPolice(dto.getNumPolice());
        contrat.setFractionnement(dto.getFractionnement());
        contrat.setCodeRenouvellement(dto.getCodeRenouvellement());
        contrat.setBranche(dto.getBranche());
        contrat.setTypeContrat(dto.getTypeContrat());
        contrat.setPrimeTTC(dto.getPrimeTTC());
        contrat.setPrimeNET(dto.getPrimeNET());
        contrat.setService(dto.getService());
        contrat.setDateDebut(dto.getDateDebut());
        contrat.setNom_assure(dto.getNom_assure());
        contrat.setDateFin(dto.getDateFin());
        contrat.setAdherent(dto.getAdherent() != null ? mapAdherentDtoToEntity(dto.getAdherent()) : null);
        contrat.setCodeAgence(dto.getCodeAgence()); // <-- ajouté
        contrat.setPreambule(dto.getPreambule());
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
        contratDTO.setPrimeNET(contrat.getPrimeNET());
        contratDTO.setNom_assure(contratDTO.getNom_assure());
        contratDTO.setDateDebut(contrat.getDateDebut());
        contratDTO.setService(contrat.getService());
        contratDTO.setDateFin(contrat.getDateFin());
        contratDTO.setEditingUser(contrat.getEditingUser());
        contratDTO.setEditingStart(contrat.getEditingStart());

        if (contrat.getAdherent() != null) {
            Adherent a = contrat.getAdherent();
            contratDTO.setAdherent(new AdherentResponseDTO(
                    a.getCodeId(), a.getNomRaison(), a.getAdresse(), a.getActivite(),a.isNouveau()
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


   /* public double calculerPrimeTTC(Contrat contrat, Tarif tarif) {
        List<Section> sections = sectionRepository.findByContrat_NumPolice(contrat.getNumPolice());

        // 🔹 Calcul total des primes nettes (garanties + RC)
        double sommePrimesNettes = calculerSommeTotalePrimesNettes(sections);

        System.out.println("📊 Somme totale des primes nettes (garanties + RC): " + sommePrimesNettes);

        // 🔹 Calcul de la prime TTC
        double primeTTC = (sommePrimesNettes + tarif.getFq())
                + ((sommePrimesNettes + 2) * tarif.getTaux())
                + tarif.getFeFg();

        // 🔹 Ajouter le prix d’adhésion si nouvel adhérent
        if (contrat.getAdherent() != null && contrat.getAdherent().isNouveau()) {
            primeTTC += tarif.getPrixAdhesion();
        }

        // 🔹 Ajustement selon le fractionnement
        int diviseur = 1;
        if (contrat.getFractionnement() != null) {
            switch (contrat.getFractionnement()) {
                case ZERO -> diviseur = 1;
                case UN   -> diviseur = 2;
                case DEUX -> diviseur = 3;
            }
        }

        primeTTC = primeTTC / diviseur;

        System.out.println("💰 Prime TTC finale: " + primeTTC);
        return primeTTC;
    }*/
   public double calculerPrimeTTC(Contrat contrat, Tarif tarif) {
       List<Section> sections = sectionRepository.findByContrat_NumPolice(contrat.getNumPolice());

       // 🔹 Calcul total des primes nettes (garanties + RC)
       double sommePrimesNettes = calculerSommeTotalePrimesNettes(sections);

       System.out.println("📊 Somme totale des primes nettes (garanties + RC): " + sommePrimesNettes);

       // 🔹 Prime nette + FQ
       double base = sommePrimesNettes + tarif.getFq();

       // 🔹 Calcul de la prime TTC selon la formule
       double primeTTC = base + (base * tarif.getTaux()) + tarif.getFeFg();

       // 🔹 Ajouter le prix d’adhésion si nouvel adhérent
       if (contrat.getAdherent() != null && contrat.getAdherent().isNouveau()) {
           primeTTC += tarif.getPrixAdhesion();
       }

       // 🔹 Ajustement selon le fractionnement
       int diviseur = 1;
       if (contrat.getFractionnement() != null) {
           switch (contrat.getFractionnement()) {
               case ZERO -> diviseur = 1;
               case UN   -> diviseur = 2;
               case DEUX -> diviseur = 3;
           }
       }

       primeTTC = primeTTC / diviseur;

       System.out.println("💰 Prime TTC finale: " + primeTTC);
       return primeTTC;
   }


    /**
     * 🔹 Calcule la somme totale des primes nettes (garanties + RC)
     */
    private double calculerSommeTotalePrimesNettes(List<Section> sections) {
        double sommePrimesNettes = 0.0;

        for (Section section : sections) {
            // Garanties classiques
            List<GarantieSection> garanties = garantieSectionRepository.findBySection_Id(section.getId());
            for (GarantieSection gs : garanties) {
                sommePrimesNettes += gs.getPrimeNET() != null ? gs.getPrimeNET() : 0;
            }

            // RC (même si RC identique dans une autre section)
            RC_Exploitation rc = section.getRcExploitation();
            if (rc != null && rc.getPrimeNET() != null) {
                sommePrimesNettes += rc.getPrimeNET();
                System.out.println("✅ RC ajouté pour la section " + section.getId() + " - Prime: " + rc.getPrimeNET());
            }
        }

        return sommePrimesNettes;
    }

}
