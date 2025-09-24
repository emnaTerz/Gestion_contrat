package com.emna.micro_service2.dto.Responses;



import java.util.List;

public class GarantieSectionResponseDTO {
    private Long id;
    private Long sectionId;
    private Long sousGarantieId;
    private Long franchise;
    private Double maximum;
    private Double minimum;
    private Double capitale;
    private Double primeNet;
    private List<ExclusionGarantieResponseDTO> exclusions;

    public GarantieSectionResponseDTO() {}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSectionId() { return sectionId; }
    public void setSectionId(Long sectionId) { this.sectionId = sectionId; }

    public Long getSousGarantieId() { return sousGarantieId; }
    public void setSousGarantieId(Long sousGarantieId) { this.sousGarantieId = sousGarantieId; }

    public Long getFranchise() {
        return franchise;
    }

    public void setFranchise(Long franchise) {
        this.franchise = franchise;
    }



    public Double getMaximum() { return maximum; }
    public void setMaximum(Double maximum) { this.maximum = maximum; }

    public Double getMinimum() { return minimum; }
    public void setMinimum(Double minimum) { this.minimum = minimum; }

    public Double getCapitale() { return capitale; }
    public void setCapitale(Double capitale) { this.capitale = capitale; }

    public Double getPrimeNet() {
        return primeNet;
    }

    public void setPrimeNet(Double primeNet) {
        this.primeNet = primeNet;
    }

    public List<ExclusionGarantieResponseDTO> getExclusions() { return exclusions; }
    public void setExclusions(List<ExclusionGarantieResponseDTO> exclusions) { this.exclusions = exclusions; }
}
