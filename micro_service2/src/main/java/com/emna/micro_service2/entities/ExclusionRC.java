package com.emna.micro_service2.entities;


import jakarta.persistence.*;

@Entity
public class ExclusionRC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    // Constructeurs, getters et setters
    public ExclusionRC() {}

    public ExclusionRC(String nom) {
        this.nom = nom;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}
