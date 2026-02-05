package com.emna.micro_service2.dto;

import com.emna.micro_service2.entities.ClauseGarantie;
public class ClauseGarantieDTO {
    private Long id;
    private String titre;
    private byte[] pdf;
    private Long sousGarantieId;

    public ClauseGarantieDTO(ClauseGarantie clause) {
        this.id = clause.getId();
        this.titre = clause.getTitre();
        this.pdf = clause.getPdf(); // récupéré correctement grâce à la transaction
        this.sousGarantieId = clause.getSousGarantie().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public Long getSousGarantieId() {
        return sousGarantieId;
    }

    public void setSousGarantieId(Long sousGarantieId) {
        this.sousGarantieId = sousGarantieId;
    }
}


