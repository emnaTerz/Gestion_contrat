package com.emna.micro_service2.entities;

import com.emna.micro_service2.entities.enums.Branche;
import jakarta.persistence.*;

@Entity
public class SousGarantie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Enumerated(EnumType.STRING)
    private Branche branche;


    @ManyToOne
    @JoinColumn(name = "garantie_id", nullable = false, foreignKey = @ForeignKey(name = "fk_sousgarantie_garantie",
            foreignKeyDefinition = "FOREIGN KEY (garantie_id) REFERENCES garantie(id) ON DELETE CASCADE"))
    private Garantie garantie;  // lien vers le catalogue global

    public SousGarantie() {}

    public SousGarantie(String nom, Garantie garantie, Branche branche) {
        this.nom = nom;
        this.garantie = garantie;
        this.branche = branche;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

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
