package com.emna.micro_service2.Service;

import com.emna.micro_service2.dto.ExclusionGarantieDTO;
import com.emna.micro_service2.entities.ExclusionGarantie;
import com.emna.micro_service2.entities.GarantieSection;
import com.emna.micro_service2.entities.Exclusion;
import com.emna.micro_service2.Repository.ExclusionGarantieRepository;
import com.emna.micro_service2.Repository.GarantieSectionRepository;
import com.emna.jwt_service.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ExclusionGarantieService {

    @Autowired
    private ExclusionGarantieRepository exclusionGarantieRepository;

    @Autowired
    private GarantieSectionRepository garantieSectionRepository;

    @Autowired
    private JwtService jwtService;

    // ------------------- CREATE OR UPDATE -------------------
    @Transactional
    public ExclusionGarantie createOrUpdateExclusion(ExclusionGarantieDTO dto, GarantieSection garantie) throws Exception {


        ExclusionGarantie exclusion = new ExclusionGarantie();

        Exclusion ex = new Exclusion();
        ex.setId(dto.getExclusionId()); // doit exister
        exclusion.setExclusion(ex);
        exclusion.setGarantieSection(garantie);

        return exclusionGarantieRepository.save(exclusion);
    }


    private void mapDtoToEntity(ExclusionGarantieDTO dto, ExclusionGarantie exclusion, GarantieSection garantie) {
        exclusion.setGarantieSection(garantie);
        Exclusion ex = new Exclusion();
        ex.setId(dto.getExclusionId());
        exclusion.setExclusion(ex);
    }
}
