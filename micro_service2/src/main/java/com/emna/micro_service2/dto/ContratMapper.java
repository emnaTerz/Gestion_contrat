package com.emna.micro_service2.dto;
import com.emna.micro_service2.entities.Contrat;
import com.emna.micro_service2.entities.RC_Exploitation;
import com.emna.micro_service2.entities.Section;
import com.emna.micro_service2.entities.ExclusionRC;

import java.util.List;
import java.util.stream.Collectors;

public class ContratMapper {

    public static ContratDTO toDTO(Contrat contrat, List<RC_Exploitation> rcExploitations, List<Section> sections) {
        if (contrat == null) return null;

        ContratDTO dto = new ContratDTO();
        dto.setNumPolice(contrat.getNumPolice());
        dto.setNom_assure(contrat.getNom_assure());
        dto.setFractionnement(contrat.getFractionnement());
        dto.setCodeRenouvellement(contrat.getCodeRenouvellement());
        dto.setBranche(contrat.getBranche());
        dto.setTypeContrat(contrat.getTypeContrat());
        dto.setCodeAgence(contrat.getCodeAgence());
        dto.setPrimeTTC(contrat.getPrimeTTC());
        dto.setDateDebut(contrat.getDateDebut());
        dto.setDateFin(contrat.getDateFin());
        dto.setEditingUser(contrat.getEditingUser());
        dto.setEditingStart(contrat.getEditingStart());
        dto.setPreambule(contrat.getPreambule());

        // Mapping Adherent
        if (contrat.getAdherent() != null) {
            dto.setAdherent(AdherentMapper.toDTO(contrat.getAdherent()));
        }

        // Mapping RC_Exploitation -> RcConfigurationDTO
        if (rcExploitations != null) {
            dto.setRcConfigurations(
                    rcExploitations.stream()
                            .filter(rc -> rc.getContrat() != null && rc.getContrat().getNumPolice().equals(contrat.getNumPolice()))
                            .map(rc -> {
                                RcConfigurationDTO rcDTO = new RcConfigurationDTO(
                                        rc.getId(),
                                        rc.getLimiteAnnuelleDomCorporels(),
                                        rc.getLimiteAnnuelleDomMateriels(),
                                        rc.getLimiteParSinistre(),
                                        rc.getFranchise(),
                                        rc.getPrimeNET(),
                                        rc.getObjetDeLaGarantie(),
                                        rc.getExclusionsRc() != null
                                                ? rc.getExclusionsRc().stream().map(ExclusionRC::getId).collect(Collectors.toList())
                                                : null,
                                        null // sectionIds seront remplis plus tard si besoin
                                );
                                return rcDTO;
                            }).collect(Collectors.toList())
            );
        }

        // Mapping Sections
        if (sections != null) {
            List<SectionDTO> sectionDTOs = sections.stream()
                    .filter(sec -> sec.getContrat() != null && sec.getContrat().getNumPolice().equals(contrat.getNumPolice()))
                    .map(sec -> {
                        SectionDTO secDTO = new SectionDTO();
                        secDTO.setIdentification(sec.getIdentification());
                        secDTO.setAdresse(sec.getAdresse());
                        secDTO.setNatureConstruction(sec.getNatureConstruction());
                        secDTO.setContiguite(sec.getContiguite());
                        secDTO.setAvoisinage(sec.getAvoisinage());
                        secDTO.setNumPolice(contrat.getNumPolice());

                        // mapping garanties si nécessaire
                        if (sec.getRcExploitation() != null) {
                            // exemple: créer une GarantieSectionDTO à partir de rcExploitation
                        }

                        return secDTO;
                    }).collect(Collectors.toList());

            dto.setSections(sectionDTOs);
        }

        return dto;
    }
}
