package com.emna.micro_service2.entities;

import jakarta.persistence.*;

@Entity
public class ExclusionGarantie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Lien vers la garantie par section
    @ManyToOne
    @JoinColumn(name = "garantie_section_id")
    private GarantieSection garantieSection;

    // Lien vers l’exclusion
    @ManyToOne
    @JoinColumn(name = "exclusion_id")
    private Exclusion exclusion;

    public ExclusionGarantie() {}

    public ExclusionGarantie(GarantieSection garantieSection, Exclusion exclusion) {
        this.garantieSection = garantieSection;
        this.exclusion = exclusion;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public GarantieSection getGarantieSection() { return garantieSection; }
    public void setGarantieSection(GarantieSection garantieSection) { this.garantieSection = garantieSection; }

    public Exclusion getExclusion() { return exclusion; }
    public void setExclusion(Exclusion exclusion) { this.exclusion = exclusion; }
}
