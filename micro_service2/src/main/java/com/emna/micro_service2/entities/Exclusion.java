package com.emna.micro_service2.entities;

import jakarta.persistence.*;

@Entity
public class Exclusion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    // Relation Many-to-One avec Garantie
    @ManyToOne
    @JoinColumn(name = "garantie_id")
    private SousGarantie garantie;

    public Exclusion() {}

    public Exclusion(String nom, SousGarantie garantie) {
        this.nom = nom;
        this.garantie = garantie;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public SousGarantie getGarantie() { return garantie; }
    public void setGarantie(SousGarantie garantie) { this.garantie = garantie; }
}
