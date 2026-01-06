package com.emna.micro_service2.entities;

import com.emna.micro_service2.entities.enums.Branche;
import jakarta.persistence.*;

@Entity
public class Exclusion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @ManyToOne
    @JoinColumn(name = "garantie_id")
    private Garantie garantie;
    @Enumerated(EnumType.STRING)
    private Branche branche;
    public Exclusion() {}

    public Exclusion(Long id, String nom, Garantie garantie,Branche branche) {
        this.id = id;
        this.nom = nom;
        this.garantie = garantie;
        this.branche = branche;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Garantie getGarantie() {
        return garantie;
    }

    public void setGarantie(Garantie garantie) {
        this.garantie = garantie;
    }

    public Branche getBranche() {
        return branche;
    }

    public void setBranche(Branche branche) {
        this.branche = branche;
    }
}
