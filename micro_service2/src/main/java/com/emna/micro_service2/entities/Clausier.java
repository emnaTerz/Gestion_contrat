package com.emna.micro_service2.entities;



import jakarta.persistence.*;

@Entity
public class Clausier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;


    @ManyToOne
    @JoinColumn(name = "garantie_id")
    private Garantie garantie;

    public Clausier() {}

    public Clausier(String nom, Garantie garantie) {
        this.nom = nom;
        this.garantie = garantie;
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
}

