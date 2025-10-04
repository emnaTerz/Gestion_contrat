package com.emna.micro_service2.dto;

import com.emna.micro_service2.entities.enums.Branche;

public class TarifDTO {
    private Long id;
    private Branche branche;
    private double fq;
    private double feFg;
    private double prixAdhesion;
    private double taux;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Branche getBranche() {
        return branche;
    }

    public void setBranche(Branche branche) {
        this.branche = branche;
    }

    public double getFq() {
        return fq;
    }

    public void setFq(double fq) {
        this.fq = fq;
    }

    public double getFeFg() {
        return feFg;
    }

    public void setFeFg(double feFg) {
        this.feFg = feFg;
    }

    public double getPrixAdhesion() {
        return prixAdhesion;
    }

    public void setPrixAdhesion(double prixAdhesion) {
        this.prixAdhesion = prixAdhesion;
    }

    public double getTaux() {
        return taux;
    }

    public void setTaux(double taux) {
        this.taux = taux;
    }
}
