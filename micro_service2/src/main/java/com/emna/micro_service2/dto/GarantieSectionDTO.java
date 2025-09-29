package com.emna.micro_service2.dto;

import com.emna.micro_service2.dto.Responses.GarantieResponseDTO;

import java.util.List;

public class GarantieSectionDTO {
    private Long sectionId;
    private Long franchise;
    private Long sousGarantieId;

    private Double maximum;
    private Double minimum;
    private Double capitale;
    private Double primeNET;
    private Double primeTTC;



    // ----------------- NOUVEAU -----------------
    private List<ExclusionGarantieDTO> exclusions;

    public GarantieSectionDTO() {}

    // Getters & Setters existants

    public Long getSectionId() { return sectionId; }
    public void setSectionId(Long sectionId) { this.sectionId = sectionId; }

    public Long getFranchise() {
        return franchise;
    }

    public void setFranchise(Long franchise) {
        this.franchise = franchise;
    }

    public Long getSousGarantieId() { return sousGarantieId; }
    public void setSousGarantieId(Long sousGarantieId) { this.sousGarantieId = sousGarantieId; }

    public Double getMaximum() { return maximum; }
    public void setMaximum(Double maximum) { this.maximum = maximum; }
    public Double getMinimum() { return minimum; }
    public void setMinimum(Double minimum) { this.minimum = minimum; }
    public Double getCapitale() { return capitale; }
    public void setCapitale(Double capitale) { this.capitale = capitale; }

    public Double getPrimeNET() {
        return primeNET;
    }

    public void setPrimeNET(Double primeNET) {
        this.primeNET = primeNET;
    }

    // ----------------- GETTERS/SETTERS NOUVEAUX -----------------
    public List<ExclusionGarantieDTO> getExclusions() { return exclusions; }
    public void setExclusions(List<ExclusionGarantieDTO> exclusions) { this.exclusions = exclusions; }

    public Double getPrimeTTC() {
        return primeTTC;
    }

    public void setPrimeTTC(Double primeTTC) {
        this.primeTTC = primeTTC;
    }
}
