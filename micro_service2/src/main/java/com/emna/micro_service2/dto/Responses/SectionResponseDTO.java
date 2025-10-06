package com.emna.micro_service2.dto.Responses;


import com.emna.micro_service2.dto.RCExploitationDTO;

import java.util.List;

public class SectionResponseDTO {
    private Long id;
    private String identification;
    private String adresse;
    private String natureConstruction;
    private String contiguite;
    private String avoisinage;
    private String numPolice; // Référence au contrat
    private List<GarantieSectionResponseDTO> garanties;
    private Long rcConfigurationId;

    public SectionResponseDTO() {}

    // Getters & Setters

    public Long getRcConfigurationId() {
        return rcConfigurationId;
    }

    public void setRcConfigurationId(Long rcConfigurationId) {
        this.rcConfigurationId = rcConfigurationId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIdentification() { return identification; }
    public void setIdentification(String identification) { this.identification = identification; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getNatureConstruction() { return natureConstruction; }
    public void setNatureConstruction(String natureConstruction) { this.natureConstruction = natureConstruction; }

    public String getContiguite() { return contiguite; }
    public void setContiguite(String contiguite) { this.contiguite = contiguite; }

    public String getAvoisinage() { return avoisinage; }
    public void setAvoisinage(String avoisinage) { this.avoisinage = avoisinage; }

    public String getNumPolice() { return numPolice; }
    public void setNumPolice(String numPolice) { this.numPolice = numPolice; }

    public List<GarantieSectionResponseDTO> getGaranties() { return garanties; }
    public void setGaranties(List<GarantieSectionResponseDTO> garanties) { this.garanties = garanties; }


}
