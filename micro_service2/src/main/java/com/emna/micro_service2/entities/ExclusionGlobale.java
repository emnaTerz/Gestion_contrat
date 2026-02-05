package com.emna.micro_service2.entities;

import com.emna.micro_service2.entities.enums.Branche;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "exclusion_globale")
public class ExclusionGlobale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Branche branche;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String libelle;


    private Integer service;

    // Constructeurs
    public ExclusionGlobale() {}

    public ExclusionGlobale(Branche branche, String libelle, Integer service) {
        this.branche = branche;
        this.libelle = libelle;
        this.service = service;
    }

    // Getters et Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Branche getBranche() {
        return branche;
    }

    public void setBranche(Branche branche) {
        this.branche = branche;
    }

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


    @Override
    public String toString() {
        return "ExclusionGlobale{" +
                "id=" + id +
                ", branche=" + branche +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
