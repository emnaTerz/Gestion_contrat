package com.emna.micro_service2.entities;



import jakarta.persistence.*;

@Entity
public class Clausier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "sous_garantie_id") // clé étrangère vers SousGarantie
    private SousGarantie sousGarantie;


    public Clausier() {}

    public Clausier(String nom, SousGarantie sousGarantie) {
        this.nom = nom;
        this.sousGarantie = sousGarantie;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public SousGarantie getGarantie() { return sousGarantie; }
    public void setGarantie(SousGarantie sousGarantie) { this.sousGarantie = sousGarantie; }
}

