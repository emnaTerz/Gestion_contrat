package com.emna.micro_service2.Service;

import com.emna.micro_service2.dto.SectionDTO;
import com.emna.micro_service2.entities.Contrat;
import com.emna.micro_service2.entities.Section;
import com.emna.micro_service2.Repository.SectionRepository;
import com.emna.micro_service2.Repository.ContratRepository;
import com.emna.jwt_service.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ContratRepository contratRepository;

    @Autowired
    private JwtService jwtService;

    // ------------------- CREATE OR UPDATE -------------------
    @Transactional
    public Section createOrUpdateSection(SectionDTO dto, HttpServletRequest request) throws Exception {
        // Récupération et validation du token
        String token = jwtService.getTokenFromRequest(request);
        if (token == null) throw new Exception("Token manquant");

        String username = jwtService.extractUserName(token);
        if (!jwtService.isTokenValid(token, username)) throw new Exception("Token invalide");

        // Vérification du contrat parent
        Contrat contrat = contratRepository.findById(dto.getNumPolice())
                .orElseThrow(() -> new Exception("Contrat introuvable"));



        Section section;

            // Création d’une nouvelle section
            section = new Section();
            section.setContrat(contrat); // Associer le contrat parent


        mapDtoToEntity(dto, section, contrat); // Mapper les champs du DTO vers l’entité

        // Sauvegarde (auto-incrément de l’ID si null)
        return sectionRepository.save(section);
    }

    // ------------------- DTO ↔ ENTITY -------------------
    private void mapDtoToEntity(SectionDTO dto, Section section, Contrat contrat) {
        section.setIdentification(dto.getIdentification());
        section.setAdresse(dto.getAdresse());
        section.setNatureConstruction(dto.getNatureConstruction());
        section.setContiguite(dto.getContiguite());
        section.setAvoisinage(dto.getAvoisinage());
        section.setContrat(contrat);
    }
}
