package com.emna.micro_service2.dto.Responses;

public class ExclusionGarantieResponseDTO {
    private Long id;
    private Long garantieSectionId;
    private Long exclusionId;

    public ExclusionGarantieResponseDTO() {}

    public ExclusionGarantieResponseDTO(Long id, Long garantieSectionId, Long exclusionId) {
        this.id = id;
        this.garantieSectionId = garantieSectionId;
        this.exclusionId = exclusionId;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getGarantieSectionId() { return garantieSectionId; }
    public void setGarantieSectionId(Long garantieSectionId) { this.garantieSectionId = garantieSectionId; }

    public Long getExclusionId() { return exclusionId; }
    public void setExclusionId(Long exclusionId) { this.exclusionId = exclusionId; }
}
