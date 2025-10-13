package com.emna.micro_service2.Service;



import com.emna.micro_service2.entities.ExclusionRC;
import com.emna.micro_service2.Repository.ExclusionRCRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExclusionRCService {

    private final ExclusionRCRepository exclusionRCRepository;

    public ExclusionRCService(ExclusionRCRepository exclusionRCRepository) {
        this.exclusionRCRepository = exclusionRCRepository;
    }
   public Optional<ExclusionRC> getExclusions(long id) {return exclusionRCRepository.findById(id);};

    // Créer une nouvelle exclusion RC
    public ExclusionRC createExclusion(String nom) {
        ExclusionRC exclusion = new ExclusionRC(nom);
        return exclusionRCRepository.save(exclusion);
    }
    public List<ExclusionRC> getAllExclusions() {
        return exclusionRCRepository.findAll();
    }
}
