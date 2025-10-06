package com.emna.micro_service2.dto;

import com.emna.micro_service2.entities.Adherent;

public class AdherentMapper {

    // Convertit une entité Adherent en DTO
    public static AdherentDTO toDTO(Adherent adherent) {
        if (adherent == null) return null;

        AdherentDTO dto = new AdherentDTO();
        dto.setCodeId(adherent.getCodeId());
        dto.setNomRaison(adherent.getNomRaison());
        dto.setAdresse(adherent.getAdresse());
        dto.setActivite(adherent.getActivite());
        dto.setNouveau(adherent.isNouveau());

        return dto;
    }

    // Convertit un DTO en entité Adherent
    public static Adherent toEntity(AdherentDTO dto) {
        if (dto == null) return null;

        Adherent adherent = new Adherent();
        adherent.setCodeId(dto.getCodeId());
        adherent.setNomRaison(dto.getNomRaison());
        adherent.setAdresse(dto.getAdresse());
        adherent.setActivite(dto.getActivite());
        adherent.setNouveau(dto.isNouveau());

        return adherent;
    }
}
