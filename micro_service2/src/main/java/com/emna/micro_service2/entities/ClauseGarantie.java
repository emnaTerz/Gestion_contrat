package com.emna.micro_service2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "clause_garantie")
public class ClauseGarantie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre; // ex: "Garantie Inondation", "Dommages électriques"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sous_garantie_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private SousGarantie sousGarantie;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore
    private byte[] pdf;



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

    public SousGarantie getSousGarantie() {
        return sousGarantie;
    }

    public void setSousGarantie(SousGarantie sousGarantie) {
        this.sousGarantie = sousGarantie;
    }
}

