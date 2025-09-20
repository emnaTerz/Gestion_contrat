package com.emna.micro_service2.Service;

import com.emna.micro_service2.Repository.SousGarantieRepository;
import com.emna.micro_service2.dto.GarantieSectionDTO;
import com.emna.micro_service2.entities.GarantieSection;
import com.emna.micro_service2.entities.Section;
import com.emna.micro_service2.entities.SousGarantie;
import com.emna.micro_service2.Repository.GarantieSectionRepository;
import com.emna.micro_service2.Repository.SectionRepository;
import com.emna.jwt_service.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class GarantieSectionService {

    @Autowired
    private GarantieSectionRepository garantieSectionRepository;

    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private SousGarantieRepository sousGarantieRepository;

    @Autowired
    private JwtService jwtService;

    // ------------------- CREATE OR UPDATE -------------------
    @Transactional
    public GarantieSection createOrUpdateGarantie(GarantieSectionDTO dto, Section section, HttpServletRequest request) throws Exception {
        String token = jwtService.getTokenFromRequest(request);
        if (token == null) throw new Exception("Token manquant");

        String username = jwtService.extractUserName(token);
        if (!jwtService.isTokenValid(token, username)) throw new Exception("Token invalide");

        GarantieSection garantie = new GarantieSection();

        if (dto.getSousGarantieId() == null) {
            throw new Exception("SousGarantie manquante pour la garantie");
        }

        SousGarantie sousGarantie = sousGarantieRepository.findById(dto.getSousGarantieId())
                .orElseThrow(() -> new Exception("SousGarantie introuvable avec l'ID: " + dto.getSousGarantieId()));

        garantie.setSection(section); // 🔴 on passe la section déjà créée
        garantie.setSousGarantie(sousGarantie);
        garantie.setFranchise(dto.getFranchise());
        garantie.setLimite(dto.getLimite());
        garantie.setMaximum(dto.getMaximum());
        garantie.setMinimum(dto.getMinimum());
        garantie.setCapitale(dto.getCapitale());
        garantie.setPrimeNET(dto.getPrimeNET());

        return garantieSectionRepository.save(garantie);
    }


    // ------------------- DTO ↔ ENTITY -------------------
    private void mapDtoToEntity(GarantieSectionDTO dto, GarantieSection garantie, Section section) {
        garantie.setSection(section);
        garantie.setFranchise(dto.getFranchise());
        // SousGarantie mapping simplifié
        SousGarantie sg = new SousGarantie();
        sg.setId(dto.getSousGarantieId());
        garantie.setSousGarantie(sg);
        garantie.setLimite(dto.getLimite());
        garantie.setMaximum(dto.getMaximum());
        garantie.setMinimum(dto.getMinimum());
        garantie.setCapitale(dto.getCapitale());
        garantie.setPrimeNET(dto.getPrimeNET());
    }
}
