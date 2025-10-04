package com.emna.micro_service2.entities;

import com.emna.micro_service2.entities.enums.Branche;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "tarif")
public class Tarif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    private Branche branche;      // Branche du contrat
    private double fq;           // Frais de gestion ou FQ
    private double feFg;         // FE/FG
    private double prixAdhesion; // Prix d'adhésion pour nouvel adhérent
    private double taux;         // Taux pour le calcul de la prime
    public Tarif() {}
    public Tarif(Branche branche, double fq, double feFg, double prixAdhesion, double taux) {
        this.branche = branche;
        this.fq = fq;
        this.feFg = feFg;
        this.prixAdhesion = prixAdhesion;
        this.taux = taux;
    }

    // Getters & Setters


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

    public double getFq() { return fq; }
    public void setFq(double fq) { this.fq = fq; }

    public double getFeFg() { return feFg; }
    public void setFeFg(double feFg) { this.feFg = feFg; }

    public double getPrixAdhesion() { return prixAdhesion; }
    public void setPrixAdhesion(double prixAdhesion) { this.prixAdhesion = prixAdhesion; }

    public double getTaux() { return taux; }
    public void setTaux(double taux) { this.taux = taux; }
}
