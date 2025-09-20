package com.emna.micro_service2.dto;

import java.util.List;

public class SectionDTO {
    private String identification;
    private String adresse;
    private String natureConstruction;
    private String contiguite;
    private String avoisinage;
    private String NumPolice; // Référence au Contrat

    // ----------------- NOUVEAU -----------------
    private List<GarantieSectionDTO> garanties;

    public SectionDTO() {}

    // Getters & Setters existants

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
    public String getNumPolice() { return NumPolice; }
    public void setNumPolice(String numPolice) { NumPolice = numPolice; }

    // ----------------- GETTERS/SETTERS NOUVEAUX -----------------
    public List<GarantieSectionDTO> getGaranties() { return garanties; }
    public void setGaranties(List<GarantieSectionDTO> garanties) { this.garanties = garanties; }
}
