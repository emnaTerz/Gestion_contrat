package com.emna.micro_service2.dto;

public class ExclusionGarantieDTO {
    private Long garantieSectionId;
    private Long exclusionId;

    public ExclusionGarantieDTO() {}

    // Getters & Setters


    public Long getGarantieSectionId() { return garantieSectionId; }
    public void setGarantieSectionId(Long garantieSectionId) { this.garantieSectionId = garantieSectionId; }

    public Long getExclusionId() { return exclusionId; }
    public void setExclusionId(Long exclusionId) { this.exclusionId = exclusionId; }
}
