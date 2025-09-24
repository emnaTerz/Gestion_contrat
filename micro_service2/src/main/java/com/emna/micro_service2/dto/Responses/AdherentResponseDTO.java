package com.emna.micro_service2.dto.Responses;


public class AdherentResponseDTO {
    private String codeId;
    private String nomRaison;
    private String adresse;
    private String activite;
    private boolean nouveau;


    public AdherentResponseDTO() {}

    public AdherentResponseDTO(String codeId, String nomRaison, String adresse, String activite, boolean nouveau) {
        this.codeId = codeId;
        this.nomRaison = nomRaison;
        this.adresse = adresse;
        this.activite = activite;
        this.nouveau = nouveau;
    }

    // Getters & Setters
    public String getCodeId() { return codeId; }
    public void setCodeId(String codeId) { this.codeId = codeId; }

    public String getNomRaison() { return nomRaison; }
    public void setNomRaison(String nomRaison) { this.nomRaison = nomRaison; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getActivite() { return activite; }
    public void setActivite(String activite) { this.activite = activite; }

    public boolean isNouveau() {
        return nouveau;
    }

    public void setNouveau(boolean nouveau) {
        this.nouveau = nouveau;
    }
}

